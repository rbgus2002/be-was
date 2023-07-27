package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

import static webserver.Mime.findByExtension;

public class HTTPServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletResponse.class);

    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;
    private String version = "HTTP/1.1";
    private String statusCode = "OK";
    private String statusMessage = "200";

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private final DataOutputStream dos;

    public HTTPServletResponse(DataOutputStream dos) {
        this.dos = dos;
    }


    public byte[] getBody() {
        return body;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.length));
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setContentType(String extension) {
        headers.put("Content-Type", findByExtension(extension).getMimeType());
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public DataOutputStream getWriter() {
        return dos;
    }

    public String info() {
        StringBuilder line = new StringBuilder(version + " " + statusCode + " " + statusMessage + " \r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            line.append(header.getKey() + " : " + header.getValue() + " \r\n");
        }
        //append (StringBuilder)
        line.append("\r\n");
        return line.toString();
    }
}
