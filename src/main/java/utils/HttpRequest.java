package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.StringUtils.NEW_LINE;

public class HttpRequest {
    private final String method;
    private final String url;
    private final String version;
    private final Map<String, String> headers;
    private final String body;

    private HttpRequest(String method, String url, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));

        // Request Line 파싱
        String line = br.readLine();
        String[] requestLine = line.split("\\s");
        String method = requestLine[0];
        String url = requestLine[1];
        String httpVersion = requestLine[2];

        // Header 파싱
        Map<String, String> headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] headerForm = line.split(":\\s");
            headers.put(headerForm[0], headerForm[1]);
        }

        // Body 파싱
        StringBuilder bodyBuilder = new StringBuilder();
        while (br.ready()) {
            bodyBuilder.append((char) br.read());
        }
        String body = bodyBuilder.toString();

        return new HttpRequest(method, url, httpVersion, headers, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE)
                .append("[Method] ").append(method).append(NEW_LINE)
                .append("[URL] ").append(url).append(NEW_LINE)
                .append("[Version] ").append(version).append(NEW_LINE);

        for (Entry<String, String> entry : headers.entrySet()) {
            sb.append("[Header] ").append(entry.getKey()).append(": ").append(entry.getValue()).append(NEW_LINE);
        }
        sb.append("[Body] ").append(body).append(NEW_LINE);

        return sb.toString();
    }
}