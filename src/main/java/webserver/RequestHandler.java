package webserver;

import controller.Controller;
import modelview.ModelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.HttpRequest;
import utils.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import static common.HttpRequest.Method;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // HTTP Request Message를 읽어서 HttpRequest 객체 생성
            HttpRequest request = HttpRequestUtils.createRequest(in);
            logger.debug(request.toString());

            // HTTP Request를 처리할 컨트롤러 가져오기
            Controller controller = fetchController(request);

            // 컨트롤러 실행 -> [모델 + 뷰] 객체 반환
            ModelView modelView = controller.process(request);

            // 실제 위치로 반환
            String path = PathResolver.resolve(modelView.getViewName());
            byte[] body = Files.readAllBytes(new File(path).toPath());

            // TODO : 적절한 응답 메세지 만들어서 보내기
            // HTTP Response Message 보내기
            sendHttpResponseMessage(out, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // TODO : static 폴더에 없는 파일을 찾을 때 예외 발생 시키기
    private Controller fetchController(HttpRequest request) {
        RequestControllerMapper mapper = RequestControllerMapper.getInstance();
        String path = request.getPath();
        Method method = request.getMethod();

        Controller controller;
        if (requestStaticFile(path)) {
            controller = mapper.get("/static", method);
        }
        else if (!mapper.contains(path, method)) {
            controller = mapper.get("/error", Method.GET);
        }
        else {
            controller = mapper.get(path, method);
        }
        return controller;
    }

    // TODO : ViewResolver와 중복되므로 하나로 빼놓기
    private boolean requestStaticFile(String path) {
        return path.endsWith(".css") || path.startsWith("/fonts") ||
                path.startsWith("/images") || path.startsWith("/js") ||
                path.endsWith(".ico");
    }

    private void sendHttpResponseMessage(OutputStream out, byte[] body) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}