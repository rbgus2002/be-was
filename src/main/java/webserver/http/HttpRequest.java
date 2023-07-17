package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HeaderParser;

import java.util.HashMap;

import static exception.ExceptionMessage.URL_NOT_CORRECT;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private String url;
    private final String version;
    private HashMap<String, String> queries;

    public HttpRequest(String request) {
        String[] req = request.split(" ");
        ValidRequest(req);
        this.method = req[0];
        this.queries = null;
        this.url = req[1];
        if(url.contains("?")) {
            this.queries = HeaderParser.parseQuery(url);
            this.url = HeaderParser.parseUrl(this.url);
        }
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }

    private void ValidRequest(String[] req) {
        if (req.length != 3) {
            throw new RuntimeException(URL_NOT_CORRECT);
        }
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


    public HashMap<String, String> getQueries() {
        return queries;
    }
}
