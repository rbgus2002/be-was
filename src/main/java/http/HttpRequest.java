package http;

import db.SessionManager;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static utils.StringUtils.*;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Header header;
    private final Body body;
    private List<Cookie> cookies;

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = HttpRequestLine.from(br.readLine());
        this.header = Header.from(br);
        this.cookies = parseCookies();
        this.body = Body.of(br, header.getContentLength());
    }

    public static HttpRequest from(InputStream in) throws IOException {
        return new HttpRequest(in);
    }

    private List<Cookie> parseCookies() {
        String cookieString = header.getCookieString();
        return Cookie.parse(cookieString);
    }

    /**
     * 세션에 쿠키 값으로 넘어온 sid가 존재하면 User 객체 반환,
     * sid가 존재하지 않으면 null 객체 반환
     */
    public User getUserInSession(){
        String sid = cookies.stream()
                .filter(Cookie::isSid)
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        return SessionManager.fetchUser(sid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<URI>").append(appendNewLine(requestLine.toString()));
        sb.append(appendNewLine("<HEADER>")).append(header).append(appendNewLine("</HEADER>"));
        sb.append(appendNewLine("<BODY>")).append(body).append(appendNewLine("</BODY>"));
        return sb.toString();
    }

    public String getPath() {
        return requestLine.getUri().getPath();
    }

    public Map<String, String> getQuery(){
        return requestLine.getUri().getQuery();
    }

    public Map<String, String> getBody() {
        return body.getBody();
    }




    public boolean isPostMethod() {
        return "POST".equals(requestLine.getMethod());
    }

    public boolean isGetMethod() {
        return "GET".equals(requestLine.getMethod());
    }
}