package webserver.request;

import webserver.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static utils.StringUtils.NEW_LINE;
import static webserver.WebServer.logger;

public class HttpRequestParser {
    public static final String TEMPLATES_PATH = "src/main/resources/templates";
    public static final String STATIC_PATH = "src/main/resources/static";

    public static HttpRequestMessage parseRequest(InputStream inputStream) throws BadRequestException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                stringBuilder.append(line).append(NEW_LINE);
            }

            String header = stringBuilder.toString();
            logger.debug("[request message] [header]\n{}", header);

            String[] lines = header.split(NEW_LINE);
            String[] statusLine = lines[0].split(" ");
            if (statusLine.length != 3) {
                throw new IllegalArgumentException("잘못된 Status line 입니다.");
            }

            String method = statusLine[0];
            HttpURL url = HttpRequestParser.parseUrl(URLDecoder.decode(statusLine[1], StandardCharsets.UTF_8));
            String version = statusLine[2];

            Map<String, String> headers = new HashMap<>();
            IntStream.range(1, lines.length).forEach(i -> {
                String[] tokens = lines[i].split(": ?");
                headers.put(tokens[0], tokens[1]);
            });

            char[] body = new char[0];
            if (headers.containsKey("Content-Length")) {
                body = new char[Integer.parseInt(headers.get("Content-Length"))];
                bufferedReader.read(body, 0, body.length);
            }
            logger.debug("[request message] [body]\n{}", new String(body));

            return new HttpRequestMessage(method, url, version, headers, URLDecoder.decode(new String(body), StandardCharsets.UTF_8));
        } catch (IOException | IndexOutOfBoundsException e) {
            throw new BadRequestException("잘못된 요청 메시지 입니다.");
        }
    }

    public static HttpURL parseUrl(String url) {
        int queryIndex = url.indexOf("?");

        String path = extractPath(url, queryIndex);
        String extension = extractExtension(path);
        Map<String, String> parameters = extractParameters(url, queryIndex);

        if (extension != null) {
            path = addResourcePath(path, extension);
        }

        return new HttpURL(url, extension, path, parameters);
    }

    private static String addResourcePath(String path, String extension) {
        if (extension.equals(".html")) {
            return TEMPLATES_PATH + path;
        }
        return STATIC_PATH + path;
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
        // todo 기본 경로에 접속할 경우 파서가 재설정 해주는 것이 맞는것인지..?
        if (url.equals("/")) {
            url = "/index.html";
        }
        if (queryIndex != -1) {
            return url.substring(0, queryIndex);
        }
        return url;
    }

    private static String extractExtension(String path) {
        int extensionIndex = path.lastIndexOf(".");
        if (extensionIndex != -1) {
            return path.substring(extensionIndex);
        }
        return null;
    }
}
