package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HTTPServletRequest {
    private final String method;
    private final String url;
    private final Map<String, String> query = new ConcurrentHashMap<>();
    private final String version;
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletRequest.class);


    public HTTPServletRequest(String method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
        logger.debug("method = {}, url = {}, version = {}", method, url, version);
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
}
