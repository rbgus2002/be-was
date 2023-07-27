package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PostService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static service.PostService.*;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 테스트용 게시글 세팅
        testSetup();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            ExecutorService executor = Executors.newWorkStealingPool();
            while ((connection = listenSocket.accept()) != null) {
                executor.submit(new RequestHandler(connection));
            }
        }
    }

    private static void testSetup() {
        PostService.addPost(
                "침착맨",
                "추천 웹툰: 이말년 서유기",
                ""
        );
        PostService.addPost(
                "예언자",
                "내일 MS가 블리자드를 합병할 것입니다!",
                ""
        );
        PostService.addPost(
                "호눅스",
                "안녕하세요~ 크롱입니다.",
                ""
        );
    }
}