package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static utils.StringUtils.NEW_LINE;

public class HttpRequestParser {

    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[1024]; // 읽을 버퍼 사이즈는 성능에 따라 정하기
        int bytesRead;
        while (bufferedReader.ready()) {
            bytesRead = bufferedReader.read(buffer);
            stringBuilder.append(buffer, 0, bytesRead);
        }

        String readAll = stringBuilder.toString();

        int crlfIndex = readAll.indexOf(NEW_LINE + NEW_LINE);
        String header = readAll.substring(0, crlfIndex);
        String body = "";
        if (crlfIndex > 0) {
            /* \r\n 바이트 이후 body 가 시작되므로 */
            body = readAll.substring(crlfIndex + 4);
        }

        String[] lines = header.split(NEW_LINE);
        String[] statusLine = lines[0].split(" ");
        if (statusLine.length != 3) {
            throw new IllegalArgumentException("잘못된 Status line 입니다.");
        }

        String method = statusLine[0];
        HttpURL url = HttpRequestParser.parseUrl(URLDecoder.decode(statusLine[1]));
        String version = statusLine[2];

        Map<String, String> headers = new HashMap<>();
        IntStream.range(1, lines.length).forEach(i -> {
            String[] tokens = lines[i].split(": ?");
            headers.put(tokens[0], tokens[1]);
        });

        return new HttpRequest(method, url, version, headers, body);
    }

    public static HttpURL parseUrl(String url) {
        int queryIndex = url.indexOf("?");

        String path = extractPath(url, queryIndex);
        String extension = extractExtension(path);
        Map<String, String> parameters = extractParameters(url, queryIndex);

        return new HttpURL(url, extension, path, parameters);
    }

    private static Map<String, String> extractParameters(String url, int queryIndex) {
        Map<String, String> parameters = null;

        if (queryIndex != -1) {
            String queryString = url.substring(queryIndex + 1);
            parameters = new HashMap<>();
            String[] tokens = queryString.split("&");
            for (String parameter : tokens) {
                int splitIndex = parameter.indexOf("=");
                parameters.put(parameter.substring(0, splitIndex), parameter.substring(splitIndex + 1));
            }
        }
        return parameters;
    }

    private static String extractPath(String url, int queryIndex) {
        if (queryIndex != -1) {
            return url.substring(0, queryIndex);
        }
        return url;
    }

    private static String extractExtension(String path) {
        int extensionIndex = path.indexOf(".");
        if (extensionIndex != -1) {
            return path.substring(extensionIndex);
        }
        return null;
    }
}
