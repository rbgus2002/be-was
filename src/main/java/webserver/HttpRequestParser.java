package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpRequest;

import static webserver.HttpStringUtils.HttpVersion;

public class HttpRequestParser {
    public static HttpRequest parseHttpRequest(InputStream in) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String firstLine = bufferedReader.readLine();
        parseFirstLine(builder, firstLine);
        bufferedReader.readLine();
        parseHeaders(builder, bufferedReader);
        // TODO: Body도 parse 해야 함

        return builder.build();
    }

    private static void parseHeaders(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException {
        String oneLine = bufferedReader.readLine();
        while(oneLine != null) {
            String[] header = oneLine.split(":");
            builder.setHeader(header[0].trim(), header[1].trim());
            oneLine = bufferedReader.readLine();
        }
    }

    private static void parseFirstLine(HttpRequest.Builder builder, String firstLine) {
        String[] tokens = firstLine.split(" ");
        // TODO: method가 다를 때(get이 아닐 때) BodyPublisher 설정
        builder.method(tokens[0], HttpRequest.BodyPublishers.noBody());
        builder.uri(URI.create("http://localhost:8080" + tokens[1]));
        builder.version(HttpVersion.get(tokens[2].trim()));
    }
}
