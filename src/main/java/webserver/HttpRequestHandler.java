package webserver;

public class HttpRequestHandler {
    public static byte[] handleGetStaticRequest(HttpRequest request) {
        String fileName = request.uri().toString().substring(20);
        System.out.println(fileName);
        return new byte[0];
    }
}
