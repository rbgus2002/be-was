package webserver;

import controller.UserController;
import handler.HandlerMapping;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatusCode;
import http.MIME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;
import view.View;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Map;

public class FrontController {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static FrontController instance;

    private FrontController() {
    }

    public static FrontController getInstance() {
        if (instance == null) {
            instance = new FrontController();
        }
        return instance;
    }

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse, DataOutputStream dos) throws Exception {
        logger.debug("{}", httpRequest);

        String viewName = process(httpRequest, httpResponse);

        viewResolve(viewName, httpRequest, httpResponse);

        render(httpResponse, dos);
    }

    private String process(HttpRequest httpRequest, HttpResponse httpResponse) throws IllegalAccessException, InvocationTargetException, IOException {
        Method method = HandlerMapping.getHandler(httpRequest);

        if (method != null) {
            return (String) method.invoke(UserController.getInstance(), httpRequest, httpResponse);
        }

        return httpRequest.getPath();
    }

    private void viewResolve(String viewName, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (viewName.startsWith("redirect:/")) {
            String path = Parser.parsePathFromRedirect(viewName);
            httpResponse.set302Header(path);
            return;
        }

        httpResponse.set200Header(viewName); // 헤더 설정

        // 로그인 확인
        if (isLogin(httpRequest)) {
            // 동적페이지 생성
            byte[] body;
            if (viewName.equals("/index.html")) {
                body = View.getIndex(httpRequest.getSession());
                httpResponse.setBody(body);
            } else {
                body = Files.readAllBytes(new File(STATIC_PATH + viewName).toPath());
                httpResponse.setBody(body);
            }
            return;
        }

        // 정적페이지 생성
        byte[] body;
        if (httpRequest.getMime() == MIME.HTML) {
            body = Files.readAllBytes(new File(TEMPLATE_PATH + viewName).toPath());
        } else {
            body = Files.readAllBytes(new File(STATIC_PATH + viewName).toPath());
        }
        httpResponse.setBody(body);
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return httpRequest.getSession() != null;
    }

    private void render(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        HttpStatusCode statusCode = httpResponse.getStatusCode();
        Map<String, String> headers = httpResponse.getHeaders();
        byte[] body = httpResponse.getBody();

        // 시작줄
        dos.writeBytes("HTTP/1.1 " + statusCode.getValue() + " " + statusCode.getDescription() + " \r\n");

        // 헤더
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");

        // 바디
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
