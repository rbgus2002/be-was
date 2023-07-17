package webserver;

import common.HttpRequest;
import common.HttpResponse;
import common.Method;
import controller.Controller;
import modelview.ModelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.HttpResponseUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import static common.ContentType.HTML;
import static common.ResponseCode.OK;


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

            // HTTP 요청을 처리할 컨트롤러 가져오기
            Controller controller = fetchController(request);

            // 컨트롤러가 요청을 처리
            ModelView modelView = controller.process(request);

            // 파일의 논리 경로 -> 실제 경로(full-path)로 반환
            String path = PathResolver.resolve(modelView.getViewName());

            // 파일 내용 불러오기
            byte[] body = Files.readAllBytes(new File(path).toPath());

            // Response 객체 생성
            HttpResponse response = HttpResponseUtils.createDefaultHeaderResponse(request.getVersion(), OK, HTML, body);

            // HTTP 응답 보내기
            HttpResponseUtils.sendResponse(out, response);
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

    // TODO : PathResolver와 중복되므로 하나로 빼놓기
    private boolean requestStaticFile(String path) {
        return path.endsWith(".css") || path.startsWith("/fonts") ||
                path.startsWith("/images") || path.startsWith("/js") ||
                path.endsWith(".ico");
    }
}