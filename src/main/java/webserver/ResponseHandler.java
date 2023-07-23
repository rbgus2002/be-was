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
import java.util.List;
import java.util.Objects;

import static util.StringUtils.getExtension;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private final Socket connection;
    private final SessionManager sessionManager;

    public ResponseHandler(Socket connection, SessionManager sessionManager) {
        this.connection = connection;
        this.sessionManager = sessionManager;
    }

    public void response(HttpRequest httpRequest) {
        HttpResponse httpResponse = handleHttpRequest(httpRequest);
        try (OutputStream out = this.connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            writeResponse(dos, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse handleHttpRequest(HttpRequest httpRequest) {
        Cookie sessionCookie = httpRequest.getCookie("sid");
        String sid = getSessionIdOrCreate(sessionCookie);
        Session session = sessionManager.getSession(sid);
        HttpResponse httpResponse = processHttpRequest(httpRequest, session);
        if (isCreatedSession(sessionCookie, sid)) {
            httpResponse.setCookie("sid", sid);
        }
        return httpResponse;
    }

    private String getSessionIdOrCreate(Cookie sessionCookie) {
        // session cookie가 없거나 session이 만료
        if (sessionCookie == null || sessionManager.getSession(sessionCookie.getValue()) == null) {
            return sessionManager.createSession();
        }
        return sessionCookie.getValue();
    }

    private boolean isCreatedSession(Cookie sessionCookie, String sid) {
        return sessionCookie == null || !sid.equals(sessionCookie.getValue());
    }

    private HttpResponse processHttpRequest(HttpRequest httpRequest, Session session) {
        try {
            return RequestMappingHandler.invokeMethod(httpRequest, session);
        } catch (Throwable e) {
            logger.debug("정적 파일을 응답합니다.");
            String path = httpRequest.uri().getPath();
            String extension = StringUtils.getExtension(path);
            if (!Objects.equals(extension, path)) {
                // 정적 파일 응답
                return HttpResponse.ok(path, httpRequest.mime());
            }
            logger.error("메소드를 실행하거나 정적파일을 응답하는데 오류가 발생했습니다.\n{}", (Object) e.getStackTrace());
            return HttpResponse.notFound();
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        HttpStatus status = httpResponse.getHttpStatus();
        InputStream fileInputStream;
        byte[] body;

        try {
            fileInputStream = getResourceAsStream(httpResponse.getPath());
            body = fileInputStream.readAllBytes();
            httpResponse.setBody(body);

            dos.writeBytes("HTTP/1.1 " + status.value() + " " + status.reasonPhrase() + " \r\n");
            writeHeaders(dos, httpResponse);
            dos.writeBytes("\r\n");
            writeBody(dos, httpResponse.getBody());
            logger.debug("{} response", httpResponse.getHttpStatus().value());
        } catch (IOException e) {
            fileInputStream = getResourceAsStream("/error.html");
            body = fileInputStream.readAllBytes();
            writeBody(dos, body);
        }
    }

    private void writeHeaders(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        if (httpResponse.getHttpStatus().value() / 100 == 3) {
            dos.writeBytes("Location: " + httpResponse.getPath() + "\r\n");
            dos.writeBytes("Cache-Control: no-cache, no-store, must-revalidate\r\n");
            dos.writeBytes("Pragma: no-cache\r\n");
            dos.writeBytes("Expires: 0\r\n");
            return;
        }
        List<Cookie> cookies = httpResponse.getCookies();
        dos.writeBytes("Content-Type: " + httpResponse.getContentType().getType() + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
        for (Cookie cookie : cookies) {
            dos.writeBytes("Set-Cookie: " + cookie.toString() + "\r\n");
        }
    }

    private void writeBody(DataOutputStream dos, byte[] body) throws IOException {
        if (body.length == 0)
            return;
        dos.write(body, 0, body.length);
        dos.flush();
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
