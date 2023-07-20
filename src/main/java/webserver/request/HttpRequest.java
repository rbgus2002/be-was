package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.Header;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpMethod httpMethod;
    private final String version;
    private final String path;
    private final Header header;

    private HttpRequest(String requestLine, String header) {

        String[] tokens = requestLine.split(" ");

        this.httpMethod = HttpMethod.valueOf(tokens[0]);
        this.path = tokens[1];
        this.version = tokens[2];
        this.header = Header.of(header);
    }

    public static HttpRequest of (InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String line = br.readLine();
        String requestLine = line;
        logger.debug("request line: {}",requestLine);

        StringBuilder header = new StringBuilder();
        while(!line.equals("")) {
            line = br.readLine();
            logger.debug("header: {}",line);
            header.append(line).append(StringUtils.NEWLINE);
        }

        return new HttpRequest(requestLine, header.toString());
    }
}
