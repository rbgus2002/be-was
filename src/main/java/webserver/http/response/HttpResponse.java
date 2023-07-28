package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMime;
import webserver.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String NEW_LINE = "\r\n";
    private final HttpStatus status;
    private final Map<String, String> header;
    private final byte[] body;

    public static class HttpResponseBuilder {
        private final Map<String, String> header;
        private byte[] body;

        private HttpStatus status = HttpStatus.OK;
        private String path = "/index.html";
        private HttpMime mime = HttpMime.HTML;

        public HttpResponseBuilder() throws IOException {
            this.header = new HashMap<>();
            header.put("Content-Type", getContentType() + ";charset=utf-8\r\n");
            header.put("Content-Length", "0");

            this.body = new byte[0];
        }

        public HttpResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public HttpResponseBuilder path(String path) throws IOException {
            this.path = path;
            this.body = getBody(path, getMime(path));
            this.header.put("Content-Length", String.valueOf(this.body.length));
            return this;
        }

        public HttpResponseBuilder mime(HttpMime mime) {
            this.mime = mime;
            this.header.put("Content-Type", mime.getContentType());
            return this;
        }

        public HttpResponseBuilder setCookie(String sessionId, String path) {
            this.header.put("Set-Cookie", "sid=" + sessionId + "; Path=" + path);
            return this;
        }

        public HttpResponseBuilder addHeaderParam(String key, String value) {
            this.header.put(key, value);
            return this;
        }

        private String getContentType() {
            String contentType = mime.getContentType();
            logger.debug("content type: {}", contentType);
            return contentType;
        }

        private HttpMime getMime(String uri) {
            int extensionIndex = uri.lastIndexOf(".") + 1;
            String extension = uri.substring(extensionIndex);
            return Arrays.stream(HttpMime.values())
                    .filter(mime -> mime.getExtension().equals(extension))
                    .findFirst()
                    .orElse(null);
        }

        private byte[] getBody(String path, HttpMime mime) throws IOException {
            byte[] body;

            if (mime.getExtension().equals("html")) {
                body = Files.readAllBytes(new File(HTML_PATH + path).toPath());
            } else {
                body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());
            }

            return body;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    private HttpResponse(HttpResponseBuilder builder) {
        this.status = builder.status;
        this.header = builder.header;
        this.body = builder.body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    private String setStatusLine(HttpStatus status) {
        return "HTTP/1.1 " + status.getStatusCode() + " " + status.getStatus() + " " + NEW_LINE;
    }

    private String setHeader(Map<String, String> headers) {
        StringBuilder headerString = new StringBuilder();
        for (String key : headers.keySet()) {
            headerString.append(key).append(": ").append(headers.get(key)).append(" ").append(NEW_LINE);
        }
        headerString.append(NEW_LINE);

        return headerString.toString();
    }

    public byte[] response() {
        byte[] statusBuffer =  setStatusLine(this.status).getBytes();
        byte[] headerBuffer =  setHeader(this.header).getBytes();

        byte[] responseBuffer = new byte[statusBuffer.length + headerBuffer.length + this.body.length];
        System.arraycopy(statusBuffer, 0, responseBuffer, 0, statusBuffer.length);
        System.arraycopy(headerBuffer, 0, responseBuffer, statusBuffer.length, headerBuffer.length);
        System.arraycopy(this.body, 0, responseBuffer, statusBuffer.length + headerBuffer.length, this.body.length);

        return responseBuffer;
    }
}
