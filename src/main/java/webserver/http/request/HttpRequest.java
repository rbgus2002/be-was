package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static utils.StringUtils.*;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, String> header = new HashMap<>();

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        this.requestLine = HttpRequestLine.from(line);

        while (!isNullOrBlank(line)) {
            line = br.readLine();
            if (isNullOrBlank(line)) {
                break;
            }
            StringTokenizer st = new StringTokenizer(line, ": ");
            header.put(st.nextToken(), st.nextToken());
        }
    }

    public static HttpRequest from(InputStream in) throws IOException {
        return new HttpRequest(in);
    }

    private boolean isNullOrBlank(String line) {
        return line == null || line.isBlank();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[URI] ").append(appendNewLine(requestLine.toString()));
        for (String key : header.keySet()) {
            sb.append(key + ": ").append(appendNewLine(header.get(key)));
        }
        return sb.toString();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getUri().getPath();
    }

    public Map<String, String> getQuery(){
        return requestLine.getUri().getQuery();
    }
}