package common.http;

import common.enums.ContentType;
import common.enums.ResponseCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static webserver.ServerConfig.STATIC_PATH;

public class HttpResponse {
    private ResponseLine responseLine;
    private Map<String, String> headers;
    private byte[] body;

    public String getResponseLine() {
        return responseLine.toString() + "\r\n";
    }

    public String getHeaders() {
        StringBuilder headerBuilder = new StringBuilder();

        String headerLine;
        for (Entry<String, String> header : headers.entrySet()) {
            headerLine = header.getKey() + ": " + header.getValue() + "\r\n";
            headerBuilder.append(headerLine);
        }
        return headerBuilder.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public void setResponseLine(ResponseLine responseLine) {
        this.responseLine = responseLine;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStaticContentResponse(ContentType type, String version, String path) {
        byte[] body = new byte[0];
        ResponseLine responseLine = new ResponseLine();
        HashMap<String, String> headers = new HashMap<>();

        try {
            body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());

            responseLine.setVersion(version);
            responseLine.setResponseCode(ResponseCode.OK);

            headers.put("Content-Type", type.getDescription());
            headers.put("Content-Length", String.valueOf(body.length));

        } catch (IOException e) {
            responseLine = new ResponseLine(version, ResponseCode.NOT_FOUND);

        } finally {
            this.responseLine = responseLine;
            this.headers = headers;
            this.body = body;
        }
    }

}
