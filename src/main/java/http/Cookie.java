package http;

import java.util.ArrayList;
import java.util.List;

public class Cookie {

    private String name;
    private String value;
    private int maxAge;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.maxAge = -1; // 브라우저 종료 시 쿠키 삭제
        this.path = "/";
    }

    public static List<Cookie> parseCookie(String cookieString) {
        List<Cookie> cookies = new ArrayList<>();
        String[] cookieParts = cookieString.split("; ");
        for (String cookiePart : cookieParts) {
            String[] keyValue = cookiePart.split("=");
            if (keyValue.length != 2) continue;
            Cookie cookie = new Cookie(keyValue[0], keyValue[1]);
            cookies.add(cookie);
        }
        return cookies;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=").append(value);
        if (maxAge >= 0) {
            sb.append("; Max-Age=").append(maxAge);
        }
        if (path != null) {
            sb.append("; Path=").append(path);
        }
        return sb.toString();
    }
}
