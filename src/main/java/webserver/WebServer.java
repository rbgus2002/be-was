package webserver;

import service.PostService;
import domain.User;
import service.UserService;
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

        // 컨트롤러에서 @RequestMapping을 찾아서 {(요청 경로 + 요청 메서드) : 컨트롤러 메서드} 매핑 정보를 ControllerMapper에 등록한다.
        ControllerMapper.getInstance().initialize();

        // 컨트롤러에서 @RendererMapping을 찾아서 {요청 경로 : 렌더러 객체} 매핑 정보를 RenderMapper에 등록한다.
        RendererMapper.getInstance().initialize();

        // ====== 테스트용 더미 유저 ========
        User user1 = UserService.createUser("user1", "a", "홍길동", "user1@a.com");
        User user2 = UserService.createUser("user2", "a", "이준호", "user2@a.com");

        // ====== 테스트용 더미 게시글 =======
        PostService.createPost(user1, "가나다라마바사", "가나다라마바사아자차카타파하");
        PostService.createPost(user1, "인텔리제이 사용법", "그만 알아보자.");
        PostService.createPost(user2, "맥 초기 세팅 방법", "그만 알아보자.");

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