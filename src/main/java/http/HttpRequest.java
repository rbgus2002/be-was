package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static util.Parser.*;
import static util.StringUtil.*;

public class HttpRequest {
    private String method;
    private String path;
    private MIME mime;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String httpVersion;

    public HttpRequest(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String requestLine = br.readLine();
        String[] tokens = requestLine.split(" ");

        this.method = tokens[0];
        this.path = parsePathFromUrl(tokens[1]);
        this.mime = convertExtensionToMime(getExtension(path));
        this.params = parseParamsFromUrl(tokens[1]);
        this.httpVersion = tokens[2];
        this.headers = parseHeaders(br);
    }

    private MIME convertExtensionToMime(String extension) {
        for(MIME mime : MIME.values()) {
            if(mime.getExtension().equals(extension)) {
                return mime;
            }
        }
        return null;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }
    public MIME getMime() {
        return this.mime;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getVersion() {
        return this.httpVersion;
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
