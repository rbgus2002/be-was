import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebServer;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        new WebServer(getPortNum(args))
                .run();
    }

    private static int getPortNum(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(args[0]);
        } catch (IllegalArgumentException e) {
            logger.error("포트 번호는 숫자로만 이루어져야 합니다.");
            throw e;
        }
    }
}
