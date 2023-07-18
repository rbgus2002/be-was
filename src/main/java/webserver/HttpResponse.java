package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String version;
    private int statusCode;
    private String statusText;
    private byte[] body;

    private static final Map<Integer, String> statusMap = new HashMap() {{
        put(200, "OK");
        put(404, "Not Found");
    }};

    public HttpResponse(String version, int statusCode, byte[] body) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusText = statusMap.get(statusCode);
        this.body = body;
    }

    public String version() {
        return this.version;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String statusText() {
        return this.statusText;
    }

    public byte[] body() {
        return this.body;
    }

}
