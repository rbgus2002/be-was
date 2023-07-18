package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HttpRequestHandler {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    private static final Map<String, Function> requestHandlers = new HashMap<>(){{
        put("/user/create", request -> {
            try {
                return handleUserCreateRequest(request);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }};

    public static byte[] handleRequest(HttpRequest request) throws IOException {
//        logger.info("HERE..... request path: {}", request.path());
        if(requestHandlers.containsKey(request.path())) {
            return (byte[]) requestHandlers.get(request.path()).apply(request);
        }
        return handleGetStaticRequest(request);
    }

    private static byte[] handleGetStaticRequest(HttpRequest request) throws IOException {
        String fileName = request.uri();
//        logger.info("Requested Filename: {}", fileName);

        String path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;
//        logger.info("Path: {}", path);

        return Files.readAllBytes(Paths.get(path));
    }

    private static byte[] handleUserCreateRequest(Object request) throws UnsupportedEncodingException {
        logger.info("HERE!");
        String paramString = ((HttpRequest) request).uri().split("\\?")[1];
        String[] parameters = paramString.split("&");
        Map<String, String> paramPair = new HashMap();
        for(String parameter: parameters) {
            int splitIndex = parameter.indexOf("=");
            paramPair.put(parameter.substring(0,splitIndex),
                    URLDecoder.decode(parameter.substring(splitIndex+1), "UTF-8"));
        }
        User user = new User(paramPair.get("userId"), paramPair.get("password"), paramPair.get("name"), paramPair.get("email"));
        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        return user.toString().getBytes();
    }
}
