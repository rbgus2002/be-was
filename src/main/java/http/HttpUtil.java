package http;

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
        NOT_FOUND(404, "Not Found");

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

    public static final String HEADER_HTTP = "HTTP/";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CHARSET = ";charset=utf-8";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_HTTP_VERSION = "1.1";
    public static final String HEADER_REDIRECT_LOCATION = "Location";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    public static final String HEADER_SESSION_ID = "sid=";
    public static final String HEADER_COOKIE_PATH = "; Path=/";
    public static final String INDEX_URL = "/index.html";
    public static final String LOGIN_FAILED_URL = "/user/login_failed.html";

    public static String readSingleHTTPLine(BufferedReader br) throws IOException, NullPointerException {
        return URLDecoder.decode(br.readLine(), StandardCharsets.UTF_8);
    }

    public static Map<String, String> parseParameterMap(String string) {
        // &를 기준으로 파라미터 분할
        String[] parameterList = string.split("&");
        // Map에 key-value 저장
        Map<String, String> parameterMap = new HashMap<>();
        for(String parameter: parameterList) {
            parameterMap.put(parameter.split("=")[0],
                    parameter.split("=")[1]);
        }

        return parameterMap;
    }

    public static Map<String, String> parseQueryParameter(String route) {
        // ?를 기준으로 쿼리 스트링 분할
        String[] tokens = route.split("\\?");
        if(tokens.length < 2) {
            return null;
        }
        String queryString = tokens[1];

        return parseParameterMap(queryString);
    }

    public static Map<String, String> parseBodyParameter(String body) {
        return parseParameterMap(body);
    }
}
