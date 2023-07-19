import webserver.WebServer;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            new WebServer().run();
        } else {
            int port = Integer.parseInt(args[0]);
            new WebServer(port).run();
        }
    }
}
