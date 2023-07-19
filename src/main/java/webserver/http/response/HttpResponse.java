package webserver.http.response;


import webserver.http.response.body.ResponseBody;

public class HttpResponse {

    private final ResponseMessageHeader responseMessageHeader;
    private ResponseBody responseBody;


    public HttpResponse() {
        this.responseMessageHeader = new ResponseMessageHeader();
        responseBody = null;
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


}
