package webserver.http;

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
    private final String CONTENT_LENGTH = "Content-Length";
    private final HttpRequestLine requestLine;
    private final Header header;
    private final Map<String, String> body = new HashMap<>();

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = HttpRequestLine.from(br.readLine());
        this.header = Header.from(br);
        setBody(br);
    }

    private void setBody(BufferedReader br) throws IOException {
        if(!existBody()){
            return;
        }
        int contentLength = header.getContentLength();
        char[] buffer = new char[contentLength];
        br.read(buffer);
        Uri.setQuery(body, String.valueOf(buffer));
    }

    private boolean existBody() {
        return header.containsContentLength();
    }

    public static HttpRequest from(InputStream in) throws IOException {
        return new HttpRequest(in);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[URI] ").append(appendNewLine(requestLine.toString()));
        sb.append(header);
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

    public Map<String, String> getBody() {
        return body;
    }
}