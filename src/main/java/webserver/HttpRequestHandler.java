package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static db.Database.addUser;
import static utils.ExceptionUtils.getErrorMessage;

public class HttpRequestHandler {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    private static final Map<String, Function<HttpRequest, HttpResponse>> requestHandlers = new HashMap<>() {{
        put("/user/create", HttpRequestHandler::handleUserCreateRequest);
    }};

    public static HttpResponse handleRequest(HttpRequest request) throws IOException {
        if (requestHandlers.containsKey(request.path())) {
            return requestHandlers.get(request.path()).apply(request);
        }
        return handleGetStaticRequest(request);
    }

    private static HttpResponse handleGetStaticRequest(HttpRequest request) {
        String fileName = request.uri();

        String path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;
        if(!request.contentType().equals("text/html"))
            // .html이 아닌 경우 static 쪽을 살펴보아야 한다.
            path = System.getProperty("user.dir") + "/src/main/resources/static" + fileName;

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        // TODO: file이 존재하지 않는 경우 404 response return
        byte[] body;
        try {
            body = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return createErrorResponse(request, 404);
        }

        builder.version(request.version())
                .statusCode(200)
                .contentType(request.contentType())
                .body(body);

        return builder.build();
    }

    private static HttpResponse handleUserCreateRequest(HttpRequest request) {
        Map<String, String> parameters = parseUri(request.uri());
        HttpResponse.Builder builder = HttpResponse.newBuilder();

        // TODO: verifyUser 로직 구현
        if (parameters.values().size() != 4) {
            return createErrorResponse(request, 400);
        }


        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        addUser(user);

        builder.version(request.version())
                .statusCode(200)
                .contentType(request.contentType())
                .body(user.toString().getBytes());

        return builder.build();
    }

    private static HttpResponse createErrorResponse(HttpRequest request, int statusCode) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .statusCode(statusCode)
                .body(getErrorMessage(statusCode).getBytes())
                .build();
    }

    private static Map<String, String> parseUri(String uri) {
        Map<String, String> result = new HashMap<>();
        if (!uri.contains("?") || uri.indexOf("?") == uri.length()-1) {
            return result;
        }
        String paramString = uri.substring(uri.indexOf("?") + 1);

        String[] parameters = paramString.split("&");
        if(parameters.length < 4) {
            return result;
        }
        for (String parameter : parameters) {
            int splitIndex = parameter.indexOf("=");
            if (splitIndex < 0 || splitIndex == parameter.length() - 1) break;
            result.put(parameter.substring(0, splitIndex), parameter.substring(splitIndex + 1));
        }

        return result;
    }
}
