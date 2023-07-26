package webserver.request;

import exception.badRequest.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.utils.StringUtils;
import webserver.Constants.ContentType;
import webserver.Constants.HttpMethod;
import webserver.Constants.HttpVersion;
import webserver.RequestHandler;
import webserver.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpMethod httpMethod;
    private final HttpVersion version;
    private final RequestPath path;
    private final RequestQuery requestQuery;
    private final Header header;
    private final RequestBody requestBody;

    private HttpRequest(String requestLine, String header, String body) {

        String[] tokens = requestLine.split(" ");

        String[] pathAndQueries = tokens[1].split("\\?");

        this.httpMethod = HttpMethod.valueOf(tokens[0]);
        this.version = HttpVersion.of(tokens[2]);
        this.path = RequestPath.of(pathAndQueries[0]);
        this.requestQuery = parseRequestQuery(pathAndQueries);
        this.header = Header.of(header);
        this.requestBody = parseRequestBody(body);
    }

    public static HttpRequest of(final InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String line = br.readLine();
        String requestLine = line;
        logger.debug("request line: {}", requestLine);

        int contentLength = -1;

        StringBuilder header = new StringBuilder();
        while (!line.equals("")) {
            line = br.readLine();

            if(line.startsWith("Content-Length")) contentLength = Integer.parseInt(line.substring(16));
            logger.debug("header: {}", line);
            header.append(line).append(StringUtils.NEWLINE);
        }

        String body = null;
        if(contentLength != -1) {
            char[] buffer = new char[contentLength];
            br.read(buffer, 0, contentLength);
            body = new String(buffer, 0, contentLength);
        }

        return new HttpRequest(requestLine, header.toString(), body);
    }

    private RequestQuery parseRequestQuery(final String[] pathAndQueries) {
        if(pathAndQueries.length < 2) return null;
        return RequestQuery.of(pathAndQueries[1]);
    }


    private RequestBody parseRequestBody(final String body) {
        if(body == null) return null;
        return RequestBody.of(body);
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

    public RequestBody getRequestBody() {
        if(requestBody == null) throw new MissingParameterException();
        return requestBody;
    }
}
