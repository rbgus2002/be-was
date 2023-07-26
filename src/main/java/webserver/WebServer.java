package webserver;

import domain.user.Database;
import domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final ExecutorService executorService = Executors.newWorkStealingPool();

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 컨트롤러에 등록된 매핑을 찾아서 ControllerMapper에 등록한다.
        ControllerMapper.getInstance().initialize();
        TemplateMapper.getInstance().initialize();

        // ======테스트용 더미 유자=======
        Database.addUser(new User("user1", "a", "userOne", "user1@a.com"));
        Database.addUser(new User("user2", "a", "userTwo", "user2@a.com"));
        Database.addUser(new User("user3", "a", "userThr", "user3@a.com"));
        Database.addUser(new User("user4", "a", "userFou", "user4@a.com"));
        //

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            // 클라이언트가 연결될때까지 대기한다.
            while ((connection = listenSocket.accept()) != null) {
                executorService.submit(new RequestHandler(connection));
            }
        }
    }
}