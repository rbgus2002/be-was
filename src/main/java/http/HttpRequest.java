package http;

import java.util.Map;
import java.util.UUID;

public class HttpRequest {

    private final String method;
    private final String url;
    private final String version;

    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Map<String, String> cookies;

    public HttpRequest(String method, String url, String version, Map<String, String> headers, Map<String, String> params, Map<String, String> cookies) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.headers = headers;
        this.params = params;
        this.cookies = cookies;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public HttpSession getSession() {
        String sid = getCookie("sid");
        if (sid == null) {
            return null;
        }
        if (HttpSessionManager.containsSid(sid)) {
            return HttpSessionManager.getSession(sid);
        }
        return null;
    }

    public boolean hasValidSession() {
        String sid = getCookie("sid");
        if (sid == null) {
            return false;
        }
        return HttpSessionManager.containsSid(sid);
    }
}
