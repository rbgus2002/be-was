package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParserImpl implements HttpRequestParser {
    @Override
    public HttpRequest parse(InputStream inputStream) throws IOException {
        HttpRequest.Builder builder = HttpRequest.builder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        parseStartLine(builder, bufferedReader);
        parseHeaders(builder, bufferedReader);

        return builder.build();
    }

    private static void parseStartLine(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException {
        String[] startLine = bufferedReader.readLine().split(" ");
        builder.method(HttpMethod.valueOf(startLine[0].trim()));
        builder.uri(startLine[1].trim());
        builder.version(startLine[2].substring(startLine[2].indexOf('/') + 1));
    }

    private static void parseHeaders(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException {
        String line = null;
        while(!(line = bufferedReader.readLine()).isBlank()) {
            String[] header = line.split(": ");
            builder.addHeader(header[0].trim(), header[1].trim());
        }
    }
}
