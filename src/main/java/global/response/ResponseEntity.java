package global.response;

import exception.NotFoundExtensionException;
import global.constant.ContentType;
import global.constant.Headers;
import global.constant.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseEntity {
    private final static String NEW_LINE = "\n";

    public static class Builder {
        private final Map<String, String> headers;

        private StatusCode statusCode = StatusCode.OK;
        private String responseBody = "";
        private ContentType contentType = ContentType.HTML;

        private Builder() {
            this.headers = new HashMap<>();
            setDefaultHeaders();
        }

        private void setDefaultHeaders() {
            headers.put(Headers.CONTENT_TYPE.getKey(), "text/html;charset=utf-8");
            headers.put(Headers.CONTENT_LENGTH.getKey(), "0");
        }

        public Builder statusCode(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder responseBody(String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public Builder addHeaders(Headers header, String value) {
            headers.put(header.getKey(), value);
            return this;
        }

        public Builder responseResource(String uri) throws IOException {
            final String uriWithExtension = checkFileExtension(uri);
            this.responseBody = findResource(uriWithExtension);
            this.contentType = extractContentType(uriWithExtension);
            return this;
        }

        public String build() {
            return String.join(NEW_LINE,
                    "HTTP/1.1 " + this.statusCode.getStatusCode() + " " + this.statusCode.getStatus() + " ",
                    assembleHeaders(),
                    responseBody);
        }

        private String assembleHeaders() {
            updateDefaultHeaders();
            String headers = this.headers.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue() + " ")
                    .collect(Collectors.joining(NEW_LINE));
            headers += NEW_LINE;
            return headers;
        }

        private void updateDefaultHeaders() {
            headers.put(Headers.CONTENT_TYPE.getKey(), contentType.getContentType() + ";charset=utf-8");
            headers.put(Headers.CONTENT_LENGTH.getKey(), String.valueOf(responseBody.getBytes().length));
        }

        private ContentType extractContentType(String uri) {
            String[] splitByExtension = uri.split("\\.");
            String fileType = splitByExtension[splitByExtension.length - 1];
            return ContentType.findContentType(fileType);
        }

        private String checkFileExtension(String uri) {
            if (!uri.contains(".")) {
                uri += ".html";
            }
            return uri;
        }

        private String findResource(String uri) throws IOException {
            try {
                if (!isHtmlExtension(uri)) {
                    final URL resource = getClass().getClassLoader().getResource("static" + uri);
                    return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
                }
                final URL resource = getClass().getClassLoader().getResource("templates" + uri);
                return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
            } catch (NullPointerException e) {
                throw new NotFoundExtensionException();
            }
        }

        private boolean isHtmlExtension(String uri) {
            return uri.contains(".html");
        }
    }

    private ResponseEntity(Builder builder) {
        StatusCode statusCode = builder.statusCode;
        String responseBody = builder.responseBody;
        ContentType contentType = builder.contentType;
    }

    public static Builder statusCode(StatusCode statusCode) {
        return new Builder().statusCode(statusCode);
    }

    public static Builder responseBody(String responseBody) {
        return new Builder().responseBody(responseBody);
    }
}