package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class PostParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(PostParser.class);

    private final ConcurrentHashMap<String, String> query = new ConcurrentHashMap<>();

    @Override
    public HTTPServletRequest getProperRequest(String startLine, BufferedReader br) throws IOException {
        String url = parseUrl(startLine);
        String method = parseMethod(startLine);
        String version = parseVersion(startLine);
        logger.debug("url = {}, method = {}, version = {}", url, method, version);
        parseQuery(br);
        HTTPServletRequest request = new HTTPServletRequest(method, url, version);
        request.addQuery(query);
        return request;
    }

    private String parseVersion(String startLine) {
        return startLine.split(" ")[2];
    }

    private String parseUrl(String startLine) {
        return startLine.split(" ")[1];
    }

    private String parseMethod(String startLine) {
        return startLine.split(" ")[0];
    }

    private void parseQuery(BufferedReader br) throws IOException {
        String line;
        int postDataI = 0;
        while ((line = br.readLine()) != null && (line.length() != 0)) {
            logger.debug("HTTP-HEADER: " + line);
            if (line.contains("Content-Length")) {
                postDataI = Integer.parseInt(
                        line.substring(
                                line.indexOf("Content-Length:") + 16));
            }
        }
        String postData = "";
        if (postDataI > 0) {
            char[] charArray = new char[postDataI];
            int readCount = br.read(charArray, 0, postDataI);
            postData = new String(charArray);
            logger.debug("readCount = {}", readCount);
        }

        String[] tokens = postData.split("&");
        for (String token : tokens) {
            String key = token.substring(0, token.indexOf("="));
            String value = token.substring(token.indexOf("=") + 1);
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            logger.debug("key = {}, value = {}", key, value);
            query.put(key, value);
        }
    }

}
