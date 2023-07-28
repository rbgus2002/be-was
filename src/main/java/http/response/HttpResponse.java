package http.response;

import http.Cookie;
import http.HttpHeaders;
import model.User;
import utils.Parser;
import view.Index;
import view.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static http.response.ResponseType.*;

public class HttpResponse {
    private final HttpStatusLine httpStatusLine;
    private final HttpHeaders httpHeaders;
    private final byte[] body;
    private final ResponseType responseType;

    private HttpResponse(HttpStatusLine httpStatusLine, HttpHeaders httpHeaders, byte[] body, ResponseType responseType) {
        this.httpStatusLine = httpStatusLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
        this.responseType = responseType;
    }

    public byte[] getBody() {
        return body;
    }

    public String writeHttpResponse() {
        StringBuilder sb = new StringBuilder();
        sb.append(httpStatusLine.writeHttpStatusLine());
        sb.append(httpHeaders.writeHttpHeaders(responseType));
        sb.append("\r\n");
        return sb.toString();
    }

    public static HttpResponse createStatic(String requestUri) throws IOException {
        byte[] body = Files.readAllBytes(new File(Parser.parsePath(requestUri)).toPath());
        return new HttpResponse(
                HttpStatusLine.createStaticStatusLine(),
                HttpHeaders.createStaticStatusHeaders(body.length, requestUri),
                body,
                STATIC
        );
    }

    public static HttpResponse createRedirect(String viewPath, Cookie cookie) {
        byte[] emptyBody = new byte[0];
        return new HttpResponse(
                HttpStatusLine.createRedirectStatusLine(),
                HttpHeaders.createRedirectStatusHeaders(viewPath, cookie),
                emptyBody,
                REDIRECT
        );
    }

    public static HttpResponse createLogoutRedirect(String viewPath, String sessionId) {
        byte[] emptyBody = new byte[0];
        return new HttpResponse(
                HttpStatusLine.createRedirectStatusLine(),
                HttpHeaders.createRedirectLogoutHeaders(viewPath, sessionId),
                emptyBody,
                REDIRECT
        );
    }

    public static HttpResponse createDynamic(String viewPath, User user) throws IOException {
        String renderingView = null;
        if (viewPath.equals("/index")) {
            Index index = Index.getInstance();
            renderingView = index.getRenderingView(user);
        }

        else {
            List list = List.getInstance();
            renderingView = list.getRenderingView(user);
        }

        byte[] body = renderingView.getBytes();

        return new HttpResponse(
                HttpStatusLine.createStaticStatusLine(),
                HttpHeaders.createStaticStatusHeaders(body.length, viewPath),
                body,
                DYNAMIC
        );
    }
}
