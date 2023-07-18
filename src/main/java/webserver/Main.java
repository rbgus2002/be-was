package webserver;

public class Main {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        WebServer.start(DEFAULT_PORT);
    }
}
