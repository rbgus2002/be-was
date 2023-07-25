package webserver.reponse;

public enum HttpResponseStatus {

    STATUS_200("HTTP/1.1 200 OK"),
    STATUS_302("HTTP/1.1 302 Found"),
    STATUS_400("HTTP/1.1 400 Bad Request"),
    STATUS_404("HTTP/1.1 404 Not Found"),
    STATUS_405("HTTP/1.1 405 Method Not Allowed"),
    STATUS_409("HTTP/1.1 409 Conflict"),
    STATUS_500("HTTP/1.1 500 Internal Server Error");


    private String statusLine;

    private HttpResponseStatus(String statusLine){
        this.statusLine = statusLine;
    }

    public String getStatusLine(){
        return this.statusLine;
    }
}
