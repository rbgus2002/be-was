package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private final String url;
    private final String version;

    public HttpRequest(String request) {
        logger.info("HttpRequest Create with request");
        String[] req = request.split(" ");
        this.method = req[0];
        this.url = req[1];
        this.version = req[2];
    }

    public HttpRequest(String method, String url, String version) {
        logger.info("HttpRequest Create");
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        logger.info("getUrl : " + url);
        return url;
    }
}
