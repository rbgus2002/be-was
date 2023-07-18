package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    public static HttpRequest parseHttpRequest(InputStream in) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String firstLine = bufferedReader.readLine();
        parseFirstLine(builder, firstLine);
        parseHeaders(builder, bufferedReader);
        // TODO: Body도 parse 해야 함

        return builder.build();
    }

    private static void parseHeaders(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException {
        String oneLine = bufferedReader.readLine();
        while (oneLine.length() != 0) {
            int colonIndex = oneLine.indexOf(":");
            builder.setHeader(oneLine.substring(0, colonIndex).trim(), oneLine.substring(colonIndex + 1).trim());
//            logger.info("HEADERS: {}", oneLine);
            oneLine = bufferedReader.readLine();
        }
    }

    private static void parseFirstLine(HttpRequest.Builder builder, String firstLine) {
        String[] tokens = firstLine.split(" ");
        builder.method(tokens[0].trim())
                .uri(tokens[1].trim())
                .path(tokens[1].split("\\?")[0])
                .version(tokens[2].trim());
    }
}
