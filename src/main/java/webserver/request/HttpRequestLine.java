package webserver.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestLine {
    private final String method;

    private final String path;

    private final Map<String, String> paramsMap;

    public HttpRequestLine(String requestLine) {
        String[] contents = requestLine.split(" ");

        method = contents[0];
        path = contents[1].split("[?]")[0];
        paramsMap = parseParams(requestLine);
    }

    private Map<String, String> parseParams(String line) {
        String[] params = line.split("[?]");
        Map<String, String> paramsMap = new HashMap<>();
        if(params.length > 1){
            Arrays.stream(params[1].split("[&]"))
                    .filter(param -> param.split("[=]").length == 2)
                    .forEach(param -> paramsMap.put(param.split("[=]")[0],param.split("[=]")[1]));
        }

        return paramsMap;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getValueByKey(String key) {
        return paramsMap.get(key);
    }
}
