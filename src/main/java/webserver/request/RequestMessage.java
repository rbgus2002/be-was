package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestMessage {
    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> metaData = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();

    public RequestMessage(String header) {
        String[] lines = header.split("\\n");

        String[] tokens = lines[0].split(" ");
        this.method = tokens[0];
        this.path = tokens[1].split("\\?", 2)[0];
        this.version = tokens[2];

        saveParameters(tokens[1]);
        saveMetadata(lines);
    }

    private void saveMetadata(String[] lines) {
        String[] tokens;
        for (int i = 1; i < lines.length; i++) {
            tokens = lines[i].split(":");
            metaData.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    private void saveParameters(String path) {
        String[] tokens = path.split("\\?", 2);
        if (tokens.length == 2) {
            String[] stringParameters = tokens[1].split("&");
            for (String parameter : stringParameters) {
                String[] keyValue = parameter.split("=", 2);
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
}
