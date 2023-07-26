package webserver.request;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private final String header;

    private final Map<String, String> headers;

    private final Map<String, String> cookies;

    public HttpRequestHeader(BufferedReader br, String line) throws IOException {
        header = parseHeader(br, line);
        headers = parseHeaderToMap(header);
        cookies = parseCookiesToMap(headers.get("Cookie"));
    }

    private String parseHeader(BufferedReader br, String line) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        while (line != null && !line.equals("")) {
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }
        return headerBuilder.toString();
    }

    private Map<String ,String> parseHeaderToMap(String header) {
        Map<String, String> headersMap = new HashMap<>();

        Arrays.stream(header.split("\n"))
                .filter(line -> line.split(": ").length > 1)
                .forEach(line -> headersMap.put(line.split(": ")[0].trim(), line.split(": ")[1].trim()));

        return headersMap;
    }

    private Map<String, String> parseCookiesToMap(String cookies) {
        Map<String, String> cookieMap = new HashMap<>();

        if(cookies != null) {
            Arrays.stream(cookies.split("; "))
                    .forEach(line -> cookieMap.put(line.split("=")[0].trim(), line.split("=")[1].trim()));
        }

        return cookieMap;
    }

    public String getHeaderValueByKey(String key) {
        return headers.get(key);
    }

    public String getCookieValueByKey(String key) {
        return cookies.get(key);
    }

    public String getHeaderString() {
        return this.header;
    }
}
