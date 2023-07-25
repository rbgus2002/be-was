package webserver;

import annotation.RequestMappingHandler;
import db.Session;
import db.SessionManager;
import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpUtils;
import view.ViewResolver;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static util.StringUtils.getExtension;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private final SessionManager sessionManager;

    public ResponseHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void response(DataOutputStream dos, HttpRequest httpRequest) throws Throwable {
        HttpResponse httpResponse;
        try {
            httpResponse = handleHttpRequest(httpRequest);
            writeResponse(dos, httpResponse);
        } catch (Throwable e) {
            logger.error(e.getMessage());
            httpResponse = errorHandle(httpRequest, e);
            writeResponse(dos, httpResponse);
        }
    }

    private HttpResponse handleHttpRequest(HttpRequest httpRequest) throws Throwable {
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

    private HttpResponse processHttpRequest(HttpRequest httpRequest, Session session) throws Throwable {
        String path = httpRequest.uri().getPath();
        HttpUtils.Method httpMethod = httpRequest.method();
        MethodHandle method = RequestMappingHandler.getMethod(httpMethod, path);
        if (method == null) {
            logger.debug("정적 파일을 응답합니다.");
            return HttpResponse.ok(path, httpRequest.mime());
        }
        logger.debug("controller를 실행합니다.");
        return RequestMappingHandler.invokeMethod(httpRequest, session);
    }

    private HttpResponse errorHandle(HttpRequest httpRequest, Throwable e) {
        logger.error("메소드를 실행하거나 정적파일을 응답하는데 오류가 발생했습니다.\n{}", (Object) e.getStackTrace());
        if (httpRequest.method().equals(HttpUtils.Method.GET)) {
            return HttpResponse.notFound("/error.html", Mime.HTML);
        }
        return HttpResponse.redirect("/error.html");
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws Throwable {
        HttpStatus status = httpResponse.getHttpStatus();
        Map<String, Object> viewParameters = httpResponse.getViewParameters();

        byte[] body;
        if (viewParameters.get("view") != null) {
            logger.debug("{}를 실행합니다.", viewParameters);
            body = ViewResolver.resolve(viewParameters);
        } else {
            InputStream fileInputStream = getResourceAsStream(httpResponse.getPath());
            body = fileInputStream.readAllBytes();
        }
        httpResponse.setBody(body);

        dos.writeBytes("HTTP/1.1 " + status.value() + " " + status.reasonPhrase() + " \r\n");
        writeHeaders(dos, httpResponse);
        dos.writeBytes("\r\n");
        writeBody(dos, httpResponse.getBody());
        logger.debug("{} response", httpResponse.getHttpStatus().value());
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
        InputStream fileInputStream = ResponseHandler.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates" : "/static") + path);
        if (fileInputStream == null) {
            throw new FileNotFoundException(path + "에 파일이 존재하지 않습니다.");
        }
        return fileInputStream;
    }
}
