package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpRequestHandler {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    public static byte[] handleGetStaticRequest(HttpRequest request) throws IOException {
        String fileName = request.uri().toString().substring(21);
        logger.info("Requested Filename: {}", fileName);

        String path = System.getProperty("user.dir") + "/src/main/resources/templates" + fileName;
        logger.info("Path: {}", path);

        return Files.readAllBytes(Paths.get(path));
    }
}
