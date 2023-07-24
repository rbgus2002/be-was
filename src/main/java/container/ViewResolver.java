package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public ViewResolver(String viewPath, HTTPServletResponse response) {
        this.viewPath = viewPath;
        this.response = response;
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
        byte[] body;
        if ((file = new File(STATIC_PATH.getPath() + viewPath)).exists() || (file = new File(TEMPLATE_PATH.getPath() + viewPath)).exists()) {
            body = Files.readAllBytes(file.toPath());
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
