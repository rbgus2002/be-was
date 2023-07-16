package webserver.http.response;


import webserver.http.HttpHeaders;

public abstract class HttpResponse {
    public abstract HttpHeaders getHeaders();

    public abstract void setHeader(String headerName, String value);

    public abstract byte[] getBody();

    public abstract void setBody(byte[] body);

    public static HttpResponse getInstance() {
        return new HttpRequestImpl();
    }
}
