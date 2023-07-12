package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private String url;
    private final String version;
    private final ConcurrentHashMap<String, String> querys = new ConcurrentHashMap<>();

    public HttpRequest(String request) {
        logger.info("HttpRequest Create with request");
        String[] req = request.split(" ");
        this.method = req[0];
        this.url = null;
        parseQuery(req[1]);
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }


    public String getMethod() {
        return method;
    }

    public String getUrl() {
        logger.info("getUrl : " + url);
        return url;
    }

    private void parseQuery(String url) {
        logger.info("parseQuery : " + url);
        String[] urlQuery = url.split("\\?");
        if (urlQuery.length == 1) {
            this.url = url;
            return;
        }
        this.url = urlQuery[0];
        String[] querys = urlQuery[1].split("&");
        for (String query : querys) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
            String[] parse = query.split("=");
            this.querys.put(parse[0], parse[1]);
        }
    }

    public ConcurrentHashMap<String, String> getQuerys() {
        logger.info("getQuerys");
        return querys;
    }
}
