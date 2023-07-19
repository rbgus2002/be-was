package creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
import webserver.Mime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static util.Path.STATIC_PATH;
import static util.Path.TEMPLATE_PATH;
import static webserver.Mime.HTML;
import static webserver.Mime.findByExtension;

public class AcceptCreator implements Creator {

    private static final Logger logger = LoggerFactory.getLogger(AcceptCreator.class);

    @Override
    public HTTPServletResponse getProperResponse(HTTPServletRequest request) throws IOException {
        String url = request.getUrl();
        String method = request.getMethod();
        String version = request.getVersion();
        logger.debug("url = {}, method = {}, version = {}", url, method, version);
        String extension = url.substring(url.lastIndexOf("."), url.length());
        byte[] body = getBody(extension, url);
        String header = response200Header(body.length, findByExtension(extension), version);

        return new HTTPServletResponse(body, header);
    }

    private String response200Header(int lengthOfBodyContent, Mime mime, String version) {
        String header = "";
        header += version + " 200 OK \r\n";
        header += "Content-Type: " + mime.getMimeType() + ";charset=utf-8\r\n";
        header += "Content-Length: " + lengthOfBodyContent + "\r\n";
        header += "\r\n";
        return header;
    }

    private byte[] getBody(String extension, String url) throws IOException {
        if (extension.equals(HTML.getExtension())) {
            return Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + url).toPath());
        }
        return Files.readAllBytes(new File(STATIC_PATH.getPath() + url).toPath());
    }
}
