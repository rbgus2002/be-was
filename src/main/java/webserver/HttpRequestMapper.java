package webserver;

public class HttpRequestMapper {
    private static final HttpRequestMapper requestMapper = new HttpRequestMapper();
    private HttpRequestMapper(){

    }

    public static HttpRequestMapper getInstance(){
        return requestMapper;
    }

}
