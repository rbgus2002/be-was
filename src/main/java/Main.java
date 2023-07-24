import webserver.WebServer;

public class Main {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        WebServer.on(port);
    }
}
