package util;

import model.HttpHeader;
import model.HttpRequest;
import model.RequestUri;
import model.enums.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static util.StringUtils.*;

public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    public static HttpRequest getHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String requestLine = br.readLine();

        logger.debug("request line {}", requestLine);
        String[] tokens = splitBy(requestLine, SPACE);
        RequestUri requestUri = RequestUri.of(tokens[URI_INDEX]);

        StringBuilder headerBuilder = new StringBuilder();
        String line = null;

        HttpHeader header = null;

        while (!(line = br.readLine()).equals(NO_CONTENT)) {
            headerBuilder.append(line);
            headerBuilder.append(NEXTLINE);
        }

        String[] splitMessage = splitBy(headerBuilder.toString(), NEXTLINE);
        header = HttpHeader.of(splitMessage);

        String body = getBody(br, header.getContentLength());

        return new HttpRequest.Builder()
                .requestUri(requestUri)
                .httpHeader(header)
                .method(Method.valueOf(tokens[METHOD_INDEX]))
                .protocol(tokens[PROTOCOL_INDEX])
                .body(body)
                .build();
    }

    private static String getBody(BufferedReader bufferedReader, int contentLength) {
        try {
            char[] charBuf = new char[contentLength];
            bufferedReader.read(charBuf, 0, contentLength);
            return String.valueOf(charBuf);
        } catch (NullPointerException | IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

}
