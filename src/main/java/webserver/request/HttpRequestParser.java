package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.RequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    private HttpRequestParser() {

    }

    public static HttpRequest getRequest(InputStream in) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return parseRequest(br);
    }

    private static HttpRequest parseRequest(BufferedReader br) throws IOException {
        String line = br.readLine();

        HttpRequestLine requestLine = new HttpRequestLine(line);

        HttpRequestHeader requestHeader = new HttpRequestHeader(br, line);

        String body = null;

        if(requestHeader.getHeaderValueByKey("Content-Length") != null){
            logger.debug(requestHeader.getHeaderValueByKey("Content-Length"));
            body = parseBody(br, Integer.parseInt(requestHeader.getHeaderValueByKey("Content-Length")));
        }

        return new HttpRequest(requestLine, requestHeader, body);

    }

    private static String parseBody(BufferedReader br, int contentLength) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        for (int index = 0; index < contentLength; index++){
            bodyBuilder.append((char)br.read());
        }

        return bodyBuilder.toString();
    }

}
