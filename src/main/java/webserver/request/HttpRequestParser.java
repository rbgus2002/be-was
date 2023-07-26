package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    private HttpRequestParser() {

    }

    public static HttpRequest getRequest(InputStream in) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return parseRequest(br);
    }

    private static HttpRequest parseRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] firstLine = line.split(" ");

        String method = firstLine[0];
        String url = firstLine[1].split("[?]")[0];
        Map<String, String> params = parseParams(firstLine[1]);
        String header = parseHeader(br, line);
        Map<String, String> headersMap = parseHeaderToMap(header);
        Map<String, String> cookieMap = parseCookiesToMap(headersMap.get("Cookie"));
        String body = null;

        if(headersMap.get("Content-Length") != null){
            logger.debug(headersMap.get("Content-Length"));
            body = parseBody(br, Integer.parseInt(headersMap.get("Content-Length")));
        }

        return new HttpRequest(method, url, params, header, headersMap, cookieMap, body);

    }

    private static Map<String, String> parseParams(String line) {
        String[] params = line.split("[?]");
        Map<String, String> paramsMap = new HashMap<>();
        if(params.length > 1){
            Arrays.stream(params[1].split("[&]"))
                    .filter(param -> param.split("[=]").length == 2)
                    .forEach(param -> paramsMap.put(param.split("[=]")[0],param.split("[=]")[1]));
        }

        return paramsMap;
    }

    private static String parseHeader(BufferedReader br, String line) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        while (line != null && !line.equals("")) {
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }
        return headerBuilder.toString();
    }

    private static String parseBody(BufferedReader br, int contentLength) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        for (int index = 0; index < contentLength; index++){
            bodyBuilder.append((char)br.read());
        }

        return bodyBuilder.toString();
    }

    private static Map<String ,String> parseHeaderToMap(String header) {
        Map<String, String> headersMap = new HashMap<>();

        Arrays.stream(header.split("\n"))
                .filter(line -> line.split(": ").length > 1)
                .forEach(line -> headersMap.put(line.split(": ")[0].trim(), line.split(": ")[1].trim()));

        return headersMap;
    }

    private static Map<String, String> parseCookiesToMap(String cookies) {
        Map<String, String> cookieMap = new HashMap<>();

        if(cookies != null) {
            Arrays.stream(cookies.split("; "))
                    .forEach(line -> cookieMap.put(line.split("=")[0].trim(), line.split("=")[1].trim()));
        }

        return cookieMap;
    }

}
