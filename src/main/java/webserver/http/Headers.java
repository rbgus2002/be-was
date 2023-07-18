package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class Headers {
    private static final Logger logger = LoggerFactory.getLogger(Headers.class);

    private final Map<String, String> headers;

    public Headers() {
        this.headers = new LinkedHashMap<>();
    }

    public static Headers from(BufferedReader bufferedReader) throws IOException {
        Headers headers = new Headers();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            logger.debug(line);
            int separatorIndex = line.indexOf(COLON);
            if (separatorIndex == -1) {
                break;
            }
            headers.put(line.substring(0, separatorIndex).strip(), line.substring(separatorIndex + 1).strip());
        }
        return headers;
    }

    public static Headers createDefaultHeaders(int contentLength) {
        Headers headers = new Headers();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(contentLength));
        return headers;
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((name, value) -> stringBuilder.append(appendNewLine(name + COLON + SPACE + value)));
        return stringBuilder.toString();
    }
}
