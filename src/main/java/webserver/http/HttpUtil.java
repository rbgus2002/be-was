package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    public enum MIME {
        HTML("text/html"),
        CSS("text/css"),
        JS("text/javascript"),
        ICO("image/vnd.microsoft.icon"),
        PNG("image/png"),
        JPG("image/jpeg"),
        WOFF("application/x-font-woff"),
        TTF("application/x-font-ttf");

        private final String mime;
        MIME(String mime) {
            this.mime = mime;
        }

        public String getMime() {
            return this.mime;
        }

        public static MIME getMimeByExtension(String extension) {
            return Arrays.stream(MIME.values())
                    .filter(mime -> extension.equalsIgnoreCase(mime.toString()))
                    .findFirst()
                    .orElse(null);
        }
    }
    public enum STATUS {
        OK(200, "OK"),
        CREATED(201, "Created"),
        MOVED_PERMANENTLY(301, "Moved Permanently"),
        FOUND(302, "Found"),
        SEE_OTHER(303, "See Other"),
        TEMPORARY_REDIRECT(307, "Temporary Redirect"),
        PERMANENT_REDIRECT(308, "Permanent Redirect"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error");

        private final int statusCode;
        private final String statusMessage;
        STATUS(int statusCode, String statusMessage) {
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }

        public int getStatusCode() {
            return statusCode;
        }
        public String getStatusMessage() {
            return statusMessage;
        }
    }

    public static final String STATIC_FILEPATH = "./src/main/resources/static";
    public static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";
    public static final String HEADER_HTTP = "HTTP/";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CHARSET = ";charset=utf-8";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_HTTP_VERSION = "1.1";
    public static final String HEADER_REDIRECT_LOCATION = "Location";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    public static final String HEADER_SESSION_ID = "sid=";
    public static final String HEADER_COOKIE_PATH = "; Path=/";
    public static final String INDEX_URL = "/index.html";
}
