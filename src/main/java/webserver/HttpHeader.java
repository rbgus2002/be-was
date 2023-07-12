package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class HttpHeader {

    private static final Logger logger = LoggerFactory.getLogger(HttpHeader.class);
    private String contextType = "text/plain";
    private final ConcurrentHashMap<String, String> extension = new ConcurrentHashMap<>();
    private String header = "";

    public HttpHeader() {
        logger.info("HttpHeader Create");

        extension.put(".html", "text/html");
        extension.put(".css", "text/css");
        extension.put(".js", "text/javascript");
        extension.put(".woff", "application/x-font-woff");
        extension.put(".ttf", "application/x-font-ttf");
    }
    public String response200Header(int bodyOfLength) {

        logger.info("response200Header");
        header += "HTTP/1.1 200 OK \r\n";
        header += "Content-Type: " + contextType + ";charset=utf-8\r\n";
        header += "Content-Length: " + bodyOfLength + "\r\n";
        header += "\r\n";

            return header;

    }

    public String response302Header(String redirectUrl) {
        logger.info("response302Header");
        header += "HTTP/1.1 302 \r\n";
        header += "Location: http://localhost:8080" + redirectUrl + "\r\n";
        header += "Content-Type: text/html;charset=UTF-8\r\n";
        header += "Content-Length: 0 \r\n";
        header += "\r\n";

        return header;
    }

    public String response404Header() {
            header += "HTTP/1.1 404 Not Found \r\n";

            return header;
    }


    public String getResourceUrl(String url) {

        final String TEMPLATE_URL = "./src/main/resources/templates";
        final String STATIC_URL = "./src/main/resources/static";
        final String NOT_FOUND = "";

        logger.info("resourceUrl : " + url);
        if(!url.contains(".")) {
            return NOT_FOUND;
        }
        String urlExtension = url.substring(url.lastIndexOf("."));
        contextType = extension.get(urlExtension);
        if(urlExtension.equals(".html")) {
            return TEMPLATE_URL + url;
        }
        return STATIC_URL + url;
    }
}
