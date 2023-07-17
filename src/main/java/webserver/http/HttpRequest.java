package webserver.http;

import webserver.http.util.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {

    private static final String INTER_PARAM_SEPARATOR = "[&]";
    private static final String INTRA_PARAM_SEPARATOR = "[=]";

    private final String header;
    private final String body;
    private final String method;
    private final String contentType;
    private final String pathParam;
    private final String path;
    private final String param;
    private final Map<String, String> model;

    public HttpRequest(final BufferedReader reader) throws IOException {
        String content = HttpUtil.getContent(reader);

        this.header = HttpUtil.extractHeader(content);
        this.body = HttpUtil.extractBody(content);
        this.method = HttpUtil.getMethod(header);
        this.contentType = HttpUtil.getContentType(header);
        this.pathParam = HttpUtil.getPathParam(header);
        this.path = HttpUtil.getPath(pathParam);
        this.param = HttpUtil.getParam(pathParam);
            // 이 부분 param 같은 값들 제대로 불러와지는지
        this.model = getModel();
    }

    public Map<String, String> getModel() {
        if (method.equals("GET")) {
            return parseToModel(param);
        }

        return parseToModel(body);
    }

    private Map<String, String> parseToModel(String value) {
        if (isEmptyValue(value)) {
            return new HashMap<>();
        }

        String[] parsedParam = value.split(INTER_PARAM_SEPARATOR);
        Map<String, String> queryPair = new HashMap<>();
        for (String pair : parsedParam) {
            String[] splitPair = pair.split(INTRA_PARAM_SEPARATOR);
            queryPair.put(splitPair[0], splitPair[1]);
        }

        return queryPair;
    }

    private boolean isEmptyValue(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    public String getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public String getParam() {
        return param;
    }
}
