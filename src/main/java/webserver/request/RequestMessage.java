package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestMessage {
    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> metaData = new HashMap<>();

    public RequestMessage(String header) {
        String[] lines = header.split("\\n");

        String[] tokens = lines[0].split(" ");
        this.method = tokens[0];
        this.path = tokens[1];
        this.version = tokens[2];

        for (int i = 1; i < lines.length; i++) {
            tokens = lines[i].split(":");
            metaData.put(tokens[0].trim(), tokens[1].trim());
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
}
