package webserver.http;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.EQUAL;

public class Cookie {
    static final String SID = "sid";
    static final String PATH = "Path";
    private final Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(String cookies) {
        Map<String, String> cookieMap = new LinkedHashMap<>();
        if (cookies != null && !cookies.isEmpty()) {
            Arrays.asList(cookies.split(";")).forEach(cookie -> {
                String[] keyValue = cookie.split(EQUAL);
                cookieMap.put(keyValue[0], keyValue[1]);
            });
        }
        return new Cookie(cookieMap);
    }

    public static Cookie from(String sid, String path) {
        Map<String, String> cookieMap = new LinkedHashMap<>();
        cookieMap.put(SID, sid);
        cookieMap.put(PATH, path);
        return new Cookie(cookieMap);
    }

    public static Cookie emtpyCookie() {
        Map<String, String> cookieMap = new LinkedHashMap<>();
        return new Cookie(cookieMap);
    }

    public String getSid() {
        return cookies.get(SID);
    }

    public String getValueForSetCookie() {
        return SID + EQUAL + cookies.get(SID) + "; " + PATH + EQUAL + cookies.get(PATH);
    }
}
