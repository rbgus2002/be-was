package webserver.http;

import webserver.http.response.Body;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class Headers {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private final Map<String, String> headers;

    public Headers() {
        this.headers = new LinkedHashMap<>();
    }

    public static Headers from(BufferedReader bufferedReader) throws IOException {
        Headers headers = new Headers();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            int separatorIndex = line.indexOf(COLON);
            if (isBlankLine(separatorIndex)) {
                break;
            }
            headers.put(line.substring(0, separatorIndex).strip(), line.substring(separatorIndex + 1).strip());
        }
        return headers;
    }
    public static Headers from(Body body) {
        Headers headers = new Headers();
        headers.put(CONTENT_TYPE, body.getContentType());
        headers.put(CONTENT_LENGTH, String.valueOf(body.getLength()));
        return headers;
    }

    private static boolean isBlankLine(int separatorIndex) {
        return separatorIndex == -1;
    }

    public static Headers redirectHeaders(String path) {
        Headers headers = Headers.from(Body.emptyBody());
        headers.put(LOCATION, path);
        return headers;
    }

    private void put(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((name, value) -> stringBuilder.append(appendNewLine(name + COLON + SPACE + value)));
        return stringBuilder.toString();
    }
    public void write(DataOutputStream dos) throws IOException {
        dos.writeBytes(toString());
    }

    public int getContentLength() {
        if (headers.containsKey(CONTENT_LENGTH)) {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        }
        return 0;
    }
}
