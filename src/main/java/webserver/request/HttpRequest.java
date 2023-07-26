package webserver.request;

import java.util.Map;

public class HttpRequest {
    private final String method;

    private final String url;

    private final Map<String, String> headersMap;

    private final Map<String, String> cookieMap;

    private final String header;

    private final String body;

    private final Map<String, String> paramsMap;

    public HttpRequest(){
        this.method = null;
        this.url = null;
        this.header = null;
        this.headersMap = null;
        this.body = null;
        this.cookieMap = null;
        this.paramsMap = null;
    }

    public HttpRequest(String method, String url, Map<String, String> paramsMap, String header, Map<String, String> headersMap, Map<String, String> cookieMap, String body){
        this.method = method;
        this.url = url;
        this.paramsMap = paramsMap;
        this.header = header;
        this.headersMap = headersMap;
        this.body = body;
        this.cookieMap = cookieMap;
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

    public String getHeaderValueByKey(String key) {
        return headersMap.get(key);
    }

    public String getParamValueByKey(String key){
        return paramsMap.get(key);
    }

    public String getSessionIdBySessionName(String sessionName) { return cookieMap.get(sessionName); }

}
