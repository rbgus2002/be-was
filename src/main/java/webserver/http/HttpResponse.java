package webserver.http;


public class HttpResponse {

    RequestMessageHeader requestMessageHeader;
    //todo: responseBody 넣기


    public HttpResponse() {
        this.requestMessageHeader = new RequestMessageHeader();
    }

    public RequestMessageHeader getHeader() {
        return requestMessageHeader;
    }


}
