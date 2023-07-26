package webserver.http.message;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.message.HttpHeaders.SEMI_COLON;

public class Cookie {
    public static final String SID = "sid";
    public static final String EQUAL = "=";
    public final Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie empty() {
        Map<String, String> map = new HashMap<>();
        return new Cookie(map);
    }

    public static Cookie from(String cookieString) {
        Map<String, String> cookies = new HashMap<>();
        String[] lines = cookieString.split(SEMI_COLON);
        for (String line : lines) {
            String[] token = line.split(EQUAL);
            String key = token[0];
            String value = token[1];
            cookies.put(key, value);
        }
        return new Cookie(cookies);
    }

    public String getSessionId() {
        return cookies.get(SID);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
