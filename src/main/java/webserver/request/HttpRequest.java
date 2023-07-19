package webserver.request;

import java.util.Map;

public class HttpRequest {
    private final String method;

    private final String url;

    private final String header;

    private final String body;

    private final Map<String, String> paramsMap;

    public HttpRequest(){
        this.method = null;
        this.url = null;
        this.header = null;
        this.body = null;
        paramsMap = null;
    }

    public HttpRequest(String method, String url, Map<String, String> paramsMap, String header, String body){
        this.method = method;
        this.url = url;
        this.paramsMap = paramsMap;
        this.header = header;
        this.body = body;
    }

    public String getMethod(){
        return this.method;
    }

    public String getHeader(){
        return this.header;
    }

    public String getUrl(){
        return this.url;
    }

    public String getBody(){
        return this.body;
    }

    public String getParamValueByKey(String key){
        return paramsMap.get(key);
    }

}
