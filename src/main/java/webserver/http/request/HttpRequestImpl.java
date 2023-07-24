package webserver.http.request;

import webserver.http.HttpHeaders;
import webserver.http.HttpMethod;
import webserver.myframework.session.Session;
import webserver.myframework.session.SessionManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpRequestImpl extends HttpRequest {
    private final HttpMethod httpMethod;
    private final String uri;
    private final String version;
    private final HttpHeaders headers;
    private final Map<String, String> requestParameters;
    private final byte[] body;
    private String requestSessionId;
    private final SessionManager sessionManager;

    public HttpRequestImpl(HttpMethod httpMethod,
                           String uri,
                           String version,
                           HttpHeaders headers,
                           Map<String, String> requestParameters,
                           byte[] body,
                           SessionManager sessionManager) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.requestParameters = requestParameters;
        this.body = body;
        this.sessionManager = sessionManager;
        requestSessionId = getSessionId();
    }

    private String getSessionId() {
        String sessionLine = getSessionLine();
        if(sessionLine == null) {
            return null;
        }
        return sessionLine.split("=")[1].trim();
    }

    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.getHeaderNames();
    }

    @Override
    public String getHeader(String headerName) {
        return headers.getHeaderValues(headerName);
    }

    @Override
    public Optional<String> getParameter(String parameterName) {
        return Optional.ofNullable(requestParameters.get(parameterName));
    }

    @Override
    public byte[] getBody() {
        return this.body;
    }

    @Override
    public String getBodyToString() {
        return URLDecoder.decode(
                new String(getBody(), StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
    }

    @Override
    public Session getSession() {
        return getSession(true);
    }

    @Override
    public Session getSession(boolean create) {
        Session session = null;
        if(requestSessionId != null) {
            session = sessionManager.findSession(requestSessionId);
        }
        if(session == null && create) {
            requestSessionId = String.valueOf(UUID.randomUUID());
            session = sessionManager.createSession(requestSessionId);
        }
        return session;
    }

    private String getSessionLine() {
        String cookieHeader = getHeader("Cookie");
        if(cookieHeader == null) {
            return null;
        }

        return Arrays.stream(cookieHeader.split(";"))
                .filter(cookie -> cookie.trim().startsWith(Session.SESSION_KEY))
                .findFirst()
                .orElse(null);
    }
}
