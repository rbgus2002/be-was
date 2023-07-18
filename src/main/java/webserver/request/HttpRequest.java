package webserver.request;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String version;
    private final boolean hasExtension;
    private final Map<String, String> metaData = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();

    public HttpRequest(String header) {
        String[] lines = header.split(NEW_LINE);

        String[] tokens = lines[0].split(" ");
        this.method = tokens[0];
        this.path = tokens[1].split(QUESTION_MARK, 2)[0];
        this.version = tokens[2];

        saveParameters(tokens[1]);
        saveMetadata(lines);

        if (path.split(DOT, 2).length == 2) {
            hasExtension = true;
            return;
        }
        hasExtension = false;
    }

    private void saveMetadata(String[] lines) {
        String[] tokens;
        for (int i = 1; i < lines.length; i++) {
            tokens = lines[i].split(COLON);
            metaData.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    private void saveParameters(String path) {
        String[] tokens = path.split(QUESTION_MARK, 2);
        if (tokens.length == 2) {
            String[] stringParameters = tokens[1].split(AMPERSAND);
            for (String parameter : stringParameters) {
                String[] keyValue = parameter.split(EQUALS_SIGN, 2);

                // 키 벨류가 올바르지 않는 경우의 예외 처리
                if (keyValue.length != 2) {
                    throw new IllegalArgumentException("Wrong query string format");
                }
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String getMetaData(String key) {
        return metaData.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean isHasExtension() {
        return hasExtension;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", hasExtension=" + hasExtension +
                ", metaData=" + metaData +
                ", parameters=" + parameters +
                '}';
    }
}
