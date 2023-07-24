package webserver.http;

import com.google.common.collect.Maps;

import java.net.URLDecoder;
import java.util.Map;

public class HttpUri {
    private String path;
    private Map<String, String> parameters;

    public HttpUri(String uri) {
        String[] splitUri = uri.split("\\?");
        this.path = splitUri[0];

        parameters = Maps.newHashMap();
        if (splitUri.length > 1) {
            parseParameters(URLDecoder.decode(splitUri[1]));
        }
    }

    public String getExtension() {
        if(path.lastIndexOf(".") < 0) return null;
        return path.substring(path.lastIndexOf("."));
    }
    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    private void parseParameters(String paramString) {
        String[] params = paramString.split("&");
        for (String param: params) {
            int splitIndex = param.indexOf("=");
            parameters.put(param.substring(0, splitIndex), param.substring(splitIndex+1));
        }
    }
}
