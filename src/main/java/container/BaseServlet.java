package container;

import db.Database;
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

public class BaseServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        String url = request.getUrl();
        String version = request.getVersion();
        String extension = url.substring(url.lastIndexOf("."));
        File file;
        byte[] body;
        logger.debug("url = {}", url);
        logger.debug("boolean = {}", new File(TEMPLATE_PATH.getPath() + url).exists());
        if ((file = new File(STATIC_PATH.getPath() + url)).exists() || (file = new File(TEMPLATE_PATH.getPath()+ url)).exists()) {
            body = Files.readAllBytes(file.toPath());
        }else{
            throw new IllegalArgumentException("잘못된 경로입니다.");
        }
        logger.debug("path = {}", file.toPath());

        response.setVersion(version);
        response.setContentType(extension);
        response.setBody(body);
        DataOutputStream writer = response.getWriter();

        logger.debug("info = {}", response.info());
        writer.writeBytes(response.info());
        writer.write(body, 0, body.length);
        writer.flush();
    }


}
