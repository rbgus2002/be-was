package webserver.http.request;

import utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders create(BufferedReader br) throws IOException {
        return new HttpHeaders(Parser.parseHeaders(br));
    }

    public void show(StringBuilder sb) {
        headers.forEach((key, value) -> {
            sb.append(key);
            sb.append(" : ");
            sb.append(value);
            sb.append(NEW_LINE);
        });
    }
}
