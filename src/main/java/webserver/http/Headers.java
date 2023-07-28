package webserver.http;

import webserver.http.response.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class Headers {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE = "Cookie";
    private final Map<String, String> headers;
    private Cookie cookie;

    public Headers(Map<String, String> headers, Cookie cookie) {
        this.headers = headers;
        this.cookie = cookie;
    }

    public Headers(Map<String, String> headers) {
        this.headers = headers;
        this.cookie = Cookie.emtpyCookie();
    }

    public static Headers from(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            line = decode(line);
            int separatorIndex = line.indexOf(COLON);
            if (isBlankLine(separatorIndex)) {
                break;
            }
            headers.put(line.substring(0, separatorIndex).strip(), line.substring(separatorIndex + 1).strip());
        }
        Cookie cookie = Cookie.from(headers.get(COOKIE));
        return new Headers(headers, cookie);
    }
    public static Headers from(ResponseBody body) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(CONTENT_TYPE, body.getContentType());
        headers.put(CONTENT_LENGTH, String.valueOf(body.getLength()));
        return new Headers(headers);
    }

    private static boolean isBlankLine(int separatorIndex) {
        return separatorIndex == -1;
    }

    public static Headers redirectHeaders(String path) {
        Headers headers = Headers.from(ResponseBody.emptyBody());
        headers.put(LOCATION, path);
        return headers;
    }

    private void put(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((name, value) -> stringBuilder.append(appendNewLine(name + COLON + SPACE + value)));
        return stringBuilder.toString();
    }

    public int getContentLength() {
        if (headers.containsKey(CONTENT_LENGTH)) {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        }
        return 0;
    }

    public void setCookie(String sid, String path) {
        this.cookie = Cookie.from(sid, path);
        headers.put(SET_COOKIE, cookie.getValueForSetCookie());
    }

    public String getSid() {
        return cookie.getSid();
    }
}
