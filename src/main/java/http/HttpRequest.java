package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static http.HttpRequestParser.*;
import static util.StringUtil.*;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String httpVersion;

    public HttpRequest(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String requestLine = br.readLine();

        String[] tokens = requestLine.split(" ");
        this.method = tokens[0];
        this.path = parsePathFromUrl(tokens[1]);
        this.params = parseParamsFromUrl(tokens[1]);
        this.httpVersion = tokens[2];
        this.headers = parseHeaders(br);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }


    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[request line] ")
                .append(this.method)
                .append(" ")
                .append(this.path)
                .append("?");

        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" ")
                .append(this.httpVersion);

        sb.append(appendNewLine());

        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            sb.append("[header] ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append(appendNewLine());
        }

        return sb.toString();
    }
}
