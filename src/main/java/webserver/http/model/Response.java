package webserver.http.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public void sendResponse(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        // StatusLine
        dos.writeBytes(HEADER_HTTP + version + " " +
                status.getStatusCode() + " " + status.getStatusMessage() + "\r\n");
        // Headers
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            dos.writeBytes(key + ": " + value + "\r\n");
        }
        dos.writeBytes("\r\n");
        // Body
        if(body != null) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}
