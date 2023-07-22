package webserver;

import annotation.RequestMappingHandler;
import db.Session;
import db.SessionManager;
import http.Cookie;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

import static util.StringUtils.getExtension;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private final Socket connection;

    public ResponseHandler(Socket connection) {
        this.connection = connection;
    }

    public void response(HttpRequest httpRequest) {
        HttpResponse httpResponse = handleHttpRequest(httpRequest);
        try (OutputStream out = this.connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpStatus status = httpResponse.getHttpStatus();
            if (status == HttpStatus.OK) {
                response200(dos, httpResponse);
            }
            if (status == HttpStatus.FOUND) {
                response302Header(dos, httpResponse);
            }
            if (status == HttpStatus.NOT_FOUND) {
                response404(dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse handleHttpRequest(HttpRequest httpRequest) {
        String path = httpRequest.uri().getPath();
        String extension = StringUtils.getExtension(path);

        Cookie sessionCookie = httpRequest.getCookie("sid");
        boolean isCreatedSession = false;
        String sid;
        if (sessionCookie == null || SessionManager.getSession(sessionCookie.getValue()) == null) {
            sid = SessionManager.createSession();
            isCreatedSession = true;
        } else {
            sid = sessionCookie.getValue();
        }
        Session session = SessionManager.getSession(sid);

        HttpResponse httpResponse;
        if (!Objects.equals(extension, path)) {
            // 정적 파일 응답
            httpResponse = HttpResponse.ok(path, httpRequest.mime());
        } else {
            // 잘못된 http request이면 /error.html response 생성
            try {
                httpResponse = RequestMappingHandler.invokeMethod(httpRequest, session);
            } catch (Throwable e) {
                logger.error("메소드를 실행하는데 오류가 발생했습니다.\n{}", (Object) e.getStackTrace());
                httpResponse = HttpResponse.notFound();
            }
        }

        if (isCreatedSession) {
            httpResponse.setCookie("sid", sid);
        }

        return httpResponse;
    }

    private void response200(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        try {
            InputStream fileInputStream = getResourceAsStream(httpResponse.getPath());
            byte[] body = fileInputStream.readAllBytes();
            response200Header(dos, httpResponse, body);
            responseBody(dos, body);
        } catch (IOException e) {
            response404(dos);
        }
    }

    private void response200Header(DataOutputStream dos, HttpResponse httpResponse, byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + httpResponse.getContentType().getType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            for (Cookie cookie : httpResponse.getCookies()) {
                dos.writeBytes("Set-Cookie: " + cookie.toString() + "\r\n");
            }
            dos.writeBytes("\r\n");
            logger.debug("200 response");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: " + httpResponse.getPath() + "\r\n");
            dos.writeBytes("Cache-Control: no-cache, no-store, must-revalidate\r\n");
            dos.writeBytes("Pragma: no-cache\r\n");
            dos.writeBytes("Expires: 0\r\n");
            dos.writeBytes("\r\n");
            logger.debug("302 response");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404(DataOutputStream dos) throws IOException {
        byte[] body;
        try {
            InputStream fileInputStream = getResourceAsStream("/error.html");
            body = fileInputStream.readAllBytes();
        } catch (IOException e) {
            body = "<html><body><h1>404 Not Found</h1></body></html>".getBytes();
        }
        response404Header(dos, body);
        responseBody(dos, body);
    }

    private void response404Header(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
            dos.writeBytes("Content-Type: text/html; charset=UTF-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            logger.debug("404 response");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private InputStream getResourceAsStream(String path) throws FileNotFoundException {
        String ext = getExtension(path);
        InputStream fileInputStream = StringUtils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates" : "/static") + path);
        if (fileInputStream == null) {
            throw new FileNotFoundException(path + "에 파일이 존재하지 않습니다.");
        }
        return fileInputStream;
    }
}
