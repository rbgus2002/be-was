package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class HTTPServletRequest {
    private final String method;
    private final String url;
    private final ConcurrentHashMap<String, String> query = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletRequest.class);


    public HTTPServletRequest(String startLine) {
        method = parseMethod(startLine);
        url = parseUri(startLine);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    private String parseUri(String startLine) {
        String[] tokens = startLine.split(" ");
        if (tokens[1].contains("?")) {
            String[] divideUriAndQuery = tokens[1].split("\\?");
            parseQuery(divideUriAndQuery[1]);
            return divideUriAndQuery[0];
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

    public ConcurrentHashMap<String, String> getQuery() {
        return query;
    }
}
