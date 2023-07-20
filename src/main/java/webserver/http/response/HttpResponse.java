package webserver.http.response;


import webserver.http.response.body.ResponseBody;

public class HttpResponse {

    private final ResponseMessageHeader responseMessageHeader;
    private ResponseBody responseBody;

    private String cookie;


    public HttpResponse() {
        this.responseMessageHeader = new ResponseMessageHeader();
        responseBody = null;
        cookie = null;
    }

    public ResponseMessageHeader getHeader() {
        return responseMessageHeader;
    }

    public void setBody(ResponseBody body) {
        responseBody = body;
    }

    public ResponseBody getBody() {
        return responseBody;
    }


    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}
