package view;

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

import static util.PathList.*;

public class ViewResolver {

    private final String viewPath;
    private final HTTPServletResponse response;

    private final HTTPServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public ViewResolver(String viewPath, HTTPServletResponse response, HTTPServletRequest request) {
        this.viewPath = viewPath;
        this.response = response;
        this.request = request;
    }

    public void service() throws IOException {
        if (viewPath.contains("redirect:")) {
            String path = viewPath.substring(viewPath.indexOf(":") + 1).trim();
            response.setHeader("Location", path);
            response.setStatusMessage("Found");
            response.setStatusCode("302");
            return;
        }

        File file;
        byte[] body;
        if ((file = new File(STATIC_PATH.getPath() + viewPath)).exists()) {
            body = Files.readAllBytes(file.toPath());
        } else if ((file = new File(TEMPLATE_PATH.getPath() + viewPath)).exists()) {
            User findUser = null;
            try {
                findUser = SessionManager.getSession(request);
            } catch (IllegalArgumentException e) {
                logger.debug(e.getMessage());
            }
            body = Files.readAllBytes(file.toPath());
            logger.debug("viewPath = {}, findUser = {}", viewPath, findUser);
            if (viewPath.equals("/index.html") && findUser != null) {
                body = MainView.changeToDynamic(findUser).getBytes();
            } else if (viewPath.equals("/user/list.html")) {
                body = ListView.changeToDynamic().getBytes();
            } else if (viewPath.equals("/user/profile.html") && findUser != null) {
                body = ProfileView.changeToDynamic(findUser).getBytes();
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
        writer.write(response.getBody(), 0, response.getBody().length);
        writer.flush();
    }

}
