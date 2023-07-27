package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HTTPServletRequest {
    private final String method;
    private final String url;
    private final Map<String, String> query = new HashMap<>();
    private final String version;
    private final Map<String, String> headers = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletRequest.class);


    public HTTPServletRequest(String method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
        logger.debug("method = {}, url = {}, version = {}", method, url, version);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void addQuery(Map<String, String> adder) {
        query.putAll(adder);
    }

    public void addHeader(Map<String, String> adder) {
        headers.putAll(adder);
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }

    @Override
    public String toString() {
        return "HTTPServletRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", query=" + query +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                '}';
    }
}
