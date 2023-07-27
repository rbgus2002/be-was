package webserver.http;

import webserver.http.Constants.HeaderOption;

import java.util.Arrays;
import java.util.HashMap;

import static support.utils.StringUtils.*;

public class Header {

    private final HashMap<String, String> headers;
    private Cookie cookie;

    private Header(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public static Header of(final String string) {
        String[] tokens = string.split(NEWLINE);

        HashMap<String, String> headers = new HashMap<>();

        Arrays.stream(tokens)
                .map(token -> token.split(COLON))
                .forEach(header -> headers.put(header[0].trim(), header[1].trim()));

        return new Header(headers);
    }

    public void setCookie() {
        String cookie = headers.get(HeaderOption.COOKIE);
        if(cookie != null) {
            headers.remove(HeaderOption.COOKIE);
            this.cookie = Cookie.of(cookie);
        }
    }

    public static Header createEmpty() {
        HashMap<String, String> headers = new HashMap<>();
        return new Header(headers);
    }

    public void addElement(final String key, final String value) {
        headers.put(key, value);
    }

    public String getElement(final String key) {
        return headers.get(key);
    }

    public void addCookieOption(final String key, final String value) {
        if(cookie == null) cookie = Cookie.createEmpty();
        cookie.addElement(key, value);
    }

    public String getCookieOption(final String key) {
        return cookie.getElement(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        headers.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + SPACE + entry.getValue() + NEWLINE)
                .forEach(sb::append);
        if(cookie != null) sb.append(cookie);
        sb.append(NEWLINE);
        return sb.toString();
    }
}
