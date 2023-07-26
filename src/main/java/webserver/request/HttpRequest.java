package webserver.request;

public class HttpRequest {

    private final HttpRequestLine requestLine;

    private final HttpRequestHeader httpRequestHeader;

    private final String body;

    public HttpRequest(){
        this.requestLine = null;
        //requestLine
        this.httpRequestHeader = null;
        //headerToString
        this.body = null;

    }

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader httpRequestHeader, String body){
        this.requestLine = requestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.body = body;
    }

    public String getMethod(){
        return requestLine.getMethod();
    }

    public String getHeaderString(){
        return httpRequestHeader.getHeaderString();
    }

    public String getPath(){
        return requestLine.getPath();
    }

    public String getBody(){
        return this.body;
    }

    public String getHeaderValueByKey(String key) {
        return httpRequestHeader.getHeaderValueByKey(key);
    }

    public String getParamValueByKey(String key){
        return requestLine.getValueByKey(key);
    }

    public String getSessionIdBySessionName(String sessionName) { return httpRequestHeader.getCookieValueByKey(sessionName); }

}
