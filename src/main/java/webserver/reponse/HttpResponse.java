package webserver.reponse;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private HttpResponseStatus status;
    private Map<String, String> header = new HashMap<>();
    private byte[] body;


    public void setStatus(HttpResponseStatus status) {
        this.status = status;
    }

    public void setBodyByFile(byte[] body, Type type) {
        this.body = body;
        setHeader("Content-Type", type.getContentType());
        setHeader("Content-Length", String.valueOf(this.body.length));
    }

    public void setBodyByText(String body) {
        this.body = body.getBytes();
        setHeader("Content-Type", Type.TEXT.getContentType());
        setHeader("Content-Length", String.valueOf(this.body.length));
    }

    public void setHeader(String key, String value){
        header.put(key, value);
    }

    public byte[] getHttpResponseBody() {
        return this.body;
    }

    public String getHttpResponseHeader() {
        return status.getStatusLine() + "\r\n" +
                getHeaderFromMap();

    }

    private String getHeaderFromMap() {
        StringBuilder headerBuilder = new StringBuilder();
        header.entrySet().stream()
                .forEach(entry -> headerBuilder.append(entry.getKey() + ": " +entry.getValue() + "\r\n"));

        return headerBuilder.toString();
    }



}
