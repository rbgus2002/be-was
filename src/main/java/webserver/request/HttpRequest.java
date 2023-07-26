package webserver.request;

import exception.badRequest.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.utils.StringUtils;
import webserver.Constants.ContentType;
import webserver.Constants.HttpMethod;
import webserver.Constants.HttpVersion;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpMethod httpMethod;
    private final HttpVersion version;
    private final RequestPath path;
    private final RequestQuery requestQuery;
    private final RequestHeader requestHeader;

    private HttpRequest(String requestLine, String header) {

        String[] tokens = requestLine.split(" ");

        String[] pathAndQueries = tokens[1].split("\\?");

        this.httpMethod = HttpMethod.valueOf(tokens[0]);
        this.version = HttpVersion.of(tokens[2]);
        this.path = RequestPath.of(pathAndQueries[0]);
        this.requestQuery = parseRequestQuery(pathAndQueries);
        this.requestHeader = RequestHeader.of(header);
    }

    public static HttpRequest of(final InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String line = br.readLine();
        String requestLine = line;
        logger.debug("request line: {}", requestLine);

        StringBuilder header = new StringBuilder();
        while (!line.equals("")) {
            line = br.readLine();
            logger.debug("header: {}", line);
            header.append(line).append(StringUtils.NEWLINE);
        }

        return new HttpRequest(requestLine, header.toString());
    }

    private RequestQuery parseRequestQuery(final String[] pathAndQueries) {
        return Optional.of(pathAndQueries)
                .filter(p -> p.length == 2)
                .map(p -> RequestQuery.of(p[1]))
                .orElse(null);
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public HttpVersion getVersion() {
        return this.version;
    }

    public String getRootPath() {
        return path.getRootPath();
    }

    public String getFullPath() {
        return path.getFullPath();
    }

    public ContentType getContentType() {
        return path.getContentType();
    }

    public RequestQuery getRequestQuery() {
        if(requestQuery == null) throw new MissingParameterException();

        return requestQuery;
    }
}
