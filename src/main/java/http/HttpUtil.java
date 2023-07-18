package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
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
