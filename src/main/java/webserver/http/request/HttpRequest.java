package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HeaderParser;

import java.util.HashMap;

import static exception.ExceptionMessage.URL_NOT_CORRECT;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod method;
    private final String url;
    private final String version;
    private HashMap<String, String> queries;

    public HttpRequest(String request) {
        String[] req = request.split(" ");
        ValidRequest(req);
        method = HttpMethod.valueOf(req[0]);
        this.queries = null;
        String tmpUrl = req[1];
        if(tmpUrl.contains("?")) {
            this.queries = HeaderParser.parseQuery(tmpUrl);
            this.url = HeaderParser.parseUrl(tmpUrl);
        } else {
            this.url = tmpUrl;
        }
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }


    private void ValidRequest(String[] req) {
        if (req.length != 3) {
            throw new RuntimeException(URL_NOT_CORRECT);
        }
    }
    public HttpMethod getMethod() {
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
