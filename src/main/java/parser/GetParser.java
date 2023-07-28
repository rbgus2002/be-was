package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GetParser implements Parser {

    private final Map<String, String> query = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(GetParser.class);

    @Override
    public HTTPServletRequest getProperRequest(String startLine, BufferedReader br) throws IOException {
        String url = parseUrl(startLine);
        String method = parseMethod(startLine);
        String version = parseVersion(startLine);
        HTTPServletRequest request = new HTTPServletRequest(method, url, version);
        parseHeader(br);
        request.addHeader(headers);
        request.addQuery(query);
        return request;
    }

    private String parseVersion(String startLine) {
        return startLine.split(" ")[2];
    }

    private String parseUrl(String startLine) {
        String[] tokens = startLine.split(" ");
        if (tokens[1].contains("?")) {
            String[] divideUrlAndQuery = tokens[1].split("\\?");
            parseQuery(divideUrlAndQuery[1]);
            return divideUrlAndQuery[0];
        }
        return tokens[1];
    }

    private String parseMethod(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[0];
    }

    private void parseQuery(String parameter) {
        String[] tokens = parameter.split("&");
        for (String token : tokens) {
            String key = token.substring(0, token.indexOf("="));
            String value = token.substring(token.indexOf("=") + 1);
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            logger.debug("key = {}, value = {}", key, value);
            query.put(key, value);
        }
    }

    private void parseHeader(BufferedReader br) throws IOException {
        String header;
        while ((header = br.readLine()) != null && (header.length() != 0)) {
            logger.debug("header = {}", header);
            String[] token = header.split(":", 2);
            headers.put(token[0].trim(), token[1].trim());
        }
    }
}
