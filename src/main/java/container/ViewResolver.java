package container;

import db.SessionManager;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static util.PathList.STATIC_PATH;
import static util.PathList.TEMPLATE_PATH;

public class ViewResolver {

    private final String viewPath;
    private final HTTPServletResponse response;

    private final HTTPServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    private static final String BUTTON_LOGIN = "<li>.*?로그인.*?</li>";
    private static final String BUTTON_SIGNUP = "<li>.*?회원가입.*?</li>";

    public ViewResolver(String viewPath, HTTPServletResponse response, HTTPServletRequest request) {
        this.viewPath = viewPath;
        this.response = response;
        this.request = request;
    }

    public void service() throws IOException {
        if (viewPath.contains("redirect:")) {
            String path = viewPath.substring(viewPath.indexOf(":") + 1);
            response.setHeader("Location", path);
            response.setStatusMessage("Found");
            response.setStatusCode("302");
            return;
        }

        File file;
        byte[] body = null;

        if ((file = new File(STATIC_PATH.getPath() + viewPath)).exists()) {
            body = Files.readAllBytes(file.toPath());
        } else if ((file = new File(TEMPLATE_PATH.getPath() + viewPath)).exists()) {
            logger.debug("request = {}", request.toString());
            try {
                User findUser = SessionManager.getSession(request);
                String httpBody = new String(Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + viewPath).toPath()));

            } catch (Exception e) {
                logger.debug("로그인 상태가 아닙니다.");
                body = Files.readAllBytes(file.toPath());
            }
        } else {
            throw new IllegalArgumentException("잘못된 경로입니다.");
        }
        String extension = viewPath.substring(viewPath.lastIndexOf("."));
        response.setContentType(extension);
        response.setBody(body);
    }

    public void render() throws IOException {
        DataOutputStream writer = response.getWriter();
        writer.writeBytes(response.info());
        logger.debug("response = {}", response.info());
        writer.write(response.getBody(), 0, response.getBody().length);
        writer.flush();
    }

}
