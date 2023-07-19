package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.PathList.STATIC_PATH;
import static util.PathList.TEMPLATE_PATH;

public class PrimaryServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        String url = request.getUrl();
        String version = request.getVersion();
        String extension = url.substring(url.lastIndexOf("."));
        Path path;
        byte[] body;
        if ((path = Paths.get(STATIC_PATH + url)).isAbsolute() || (path = Paths.get(TEMPLATE_PATH + url)).isAbsolute()) {
            body = Files.readAllBytes(path);
        }else{
            throw new IllegalArgumentException("잘못된 경로입니다.");
        }
        logger.debug("path = {}", path);
        response.setVersion(version);
        response.setContentType(extension);
        response.setBody(body);
        DataOutputStream writer = response.getWriter();
        logger.debug("info = {}", response.info());
        writer.writeBytes(response.info());
        writer.flush();
    }


}
