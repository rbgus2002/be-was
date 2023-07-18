package parser;

import webserver.HTTPServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class GetParser implements Parser {

    private ConcurrentHashMap<String, String> query = new ConcurrentHashMap<>();

    @Override
    public HTTPServletRequest getProperRequest(String startLine, BufferedReader br) throws IOException {
        String url = parseUrl(startLine);
        String method = parseMethod(startLine);
        String version = parseVersion(startLine);
        HTTPServletRequest request = new HTTPServletRequest(method, url, version);
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
            String value = token.substring(token.indexOf("=") + 1, token.length());
            query.put(key, value);
        }
    }
}
