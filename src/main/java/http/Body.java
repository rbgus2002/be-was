package http;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static utils.FileIOUtils.*;

public class Body {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String HTML = "html";
    private static final String CSS = "css";

    private static final UserController userController = new UserController();

    public static byte[] createBody(String requestLine) throws IOException {
        String entireUri = splitPath(requestLine);
        String[] uri = entireUri.split("\\.");
        switch (uri[uri.length - 1]) {
            case HTML:
                return loadTemplatesFromPath(entireUri);
            case CSS:
                return loadStaticFromPath(entireUri);
            default:
                return controllerCheck(entireUri);
        }

    }

    public static byte[] loadStaticFromPath(String uri) throws IOException {
        return Files.readAllBytes(new File(STATIC_RESOURCES + uri).toPath());
    }
    public static byte[] loadTemplatesFromPath(String uri) throws IOException {
        return Files.readAllBytes(new File(TEMPLATES_RESOURCES + uri).toPath());
    }
    public static byte[] controllerCheck(String uri) {
        String[] check = uri.split("\\?");
        if (check[0].equals("/user/create")) {
            return userController.createUser(check[1]);
        }
        return null;
    }
}
