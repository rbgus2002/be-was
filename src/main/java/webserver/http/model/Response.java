package webserver.http.model;

import java.util.Map;

import static webserver.http.HttpUtil.*;

public class Response {
    private final STATUS status;
    private final String version = HEADER_HTTP_VERSION;
    private final Map<String, String> headerMap;
    private final byte[] body;

    public Response(STATUS status, Map<String, String> headerMap, byte[] body) {
        this.status = status;
        this.headerMap = headerMap;
        this.body = body;
    }

    public STATUS getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public byte[] getBody() {
        return body;
    }
}
