package webserver.http;

public class HttpResponse {

    private String stateCode;
    private String location;
    private String contentType;
    private byte[] body = new byte[0];

    private HttpResponse() {

    }

    public static HttpResponse createResponse() {
        return new HttpResponse();
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public String getResponseHead(int bodyLength) {
        StringBuilder stringBuilder = new StringBuilder();

        if (stateCode.equals("302 Found ")) {
            stringBuilder.append("HTTP/1.1 ").append(stateCode).append("\r\n");
            stringBuilder.append("Location: ").append(location).append("\r\n");
            stringBuilder.append("Content-Type: ").append(contentType).append("\r\n");
            stringBuilder.append("Content-Length: ").append(bodyLength).append("\r\n");
            stringBuilder.append("\r\n");
        } else {
            stringBuilder.append("HTTP/1.1 ").append(stateCode).append("\r\n");
            stringBuilder.append("Content-Type: ").append(contentType).append("\r\n");
            stringBuilder.append("Content-Length: ").append(bodyLength).append("\r\n");
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public void setContentType(String url) {
        String extension = getExtension(url);
        if (extension == null || extension.equals("html")) {
            contentType = "text/html;charset=utf-8";
        } else if (extension.equals("css")) {
            contentType = "text/css";
        } else if (extension.equals("js")) {
            contentType = "text/javascript";
        } else if (extension.equals("ico")) {
            contentType = "image/x-ico";
        } else if (extension.equals("png")) {
            contentType = "image/png";
        } else if (extension.equals("jpg")) {
            contentType = "image/jpeg";
        } else {
            contentType = "text/plan";
        }

    }

    private String getExtension(String path) {
        String[] pathToken = path.split("\\.");
        int length = pathToken.length;
        if (length == 1) {
            return null;
        }
        return pathToken[length - 1];
    }
}
