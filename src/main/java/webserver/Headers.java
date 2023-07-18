package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.appendNewLine;

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
            int separatorIndex = line.indexOf(":");
            if (separatorIndex == -1) {
                break;
            }
            headers.putHeader(line.substring(0, separatorIndex).strip(), line.substring(separatorIndex + 1).strip());
        }
        return headers;
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((name, value) -> stringBuilder.append(appendNewLine(name + ": " + value)));
        return stringBuilder.toString();
    }
}
