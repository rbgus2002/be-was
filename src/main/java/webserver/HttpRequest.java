package webserver;

public class HttpRequest {
    private final String method;

    private final String url;

    private final String header;

    private final String body;

    public HttpRequest(){
        this.method = null;
        this.url = null;
        this.header = null;
        this.body = null;
    }

    public HttpRequest(String method, String url, String header, String body){
        this.method = method;
        this.url = url;
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

}
