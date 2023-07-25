package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {

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
            String[] tokens = parameter.split(PARAM_SEPARATOR);
            String key = tokens[0].trim();
            String value = tokens[1].trim();
            headers.put(key, value);
        }

        return headers;
    }

    public String getParamValue(String key) {
        return header.get(key);
    }
}
