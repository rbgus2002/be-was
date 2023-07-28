package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHeaders.class);

    private static final String NEW_LINE = "\r\n";
    private static final String PARAM_SEPARATOR = ":";

    private final Map<String, String> header;

    public HttpRequestHeaders(String headerString) {
        this.header = parseHeaders(headerString);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> parseHeaders(String headerString) {
        Map<String, String> headers = new HashMap<>();

        String[] parameters = headerString.split(NEW_LINE);
        for (String parameter : parameters) {
            int colonIndex = parameter.indexOf(PARAM_SEPARATOR);
            String key = parameter.substring(0, colonIndex).trim();
            String value = parameter.substring(colonIndex + 1).trim();
            headers.put(key, value);
        }

        return headers;
    }

    public String getParamValue(String key) {
        return header.get(key);
    }
}
