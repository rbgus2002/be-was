package webserver.request;

import java.util.Collections;
import java.util.Map;

public class HttpURL {
    private final String url;
    private final String extension;
    private final String path;
    private final Map<String, String> parameters;

    public HttpURL(String url, String extension, String path, Map<String, String> parameters) {
        this.url = url;
        this.extension = extension;
        this.path = path;
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public String getUrl() {
        return url;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "HttpURL{" +
                "url='" + url + '\'' +
                ", extension='" + extension + '\'' +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
