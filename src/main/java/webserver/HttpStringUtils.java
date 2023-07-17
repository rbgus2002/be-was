package webserver;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class HttpStringUtils {
    public static Map<String, HttpClient.Version> HttpVersion = new HashMap<>() {{
        put("HTTP/1.1", HttpClient.Version.HTTP_1_1);
    }};

}
