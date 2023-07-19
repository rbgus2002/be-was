package webserver.request;

import utils.StringUtils;
import webserver.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {

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

        StringBuilder header = new StringBuilder();
        while(!line.equals("")) {
            line = br.readLine();
            header.append(line).append(StringUtils.NEWLINE);
        }

        return new HttpRequest(requestLine, header.toString());
    }
}
