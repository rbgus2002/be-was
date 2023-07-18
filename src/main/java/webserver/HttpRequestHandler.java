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
    private static final Map<String, Function<HttpRequest, HttpResponse>> requestHandlers = new HashMap<>(){{
        put("/user/create", request -> {
            try {
                return handleUserCreateRequest(request);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }};

    public static HttpResponse handleRequest(HttpRequest request) throws IOException {
        if(requestHandlers.containsKey(request.path())) {
            return requestHandlers.get(request.path()).apply(request);
        }
        return handleGetStaticRequest(request);
    }

    private static HttpResponse handleGetStaticRequest(HttpRequest request) throws IOException {
        String fileName = request.uri();

        String path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;

        // TODO: file이 존재하지 않는 경우 404 response return
        byte[] body = Files.readAllBytes(Paths.get(path));

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        builder.version(request.version())
                .statusCode(200)
                .body(body);

        return builder.build();
    }

    private static HttpResponse handleUserCreateRequest(HttpRequest request) throws UnsupportedEncodingException {
        String paramString = request.uri().split("\\?")[1];
        String[] parameters = paramString.split("&");
        Map<String, String> paramPair = new HashMap();
        // TODO: 값이 비어있는 경우? -> html에서 설정 가능할 것 같기도 함.
        for(String parameter: parameters) {
            int splitIndex = parameter.indexOf("=");
            paramPair.put(parameter.substring(0,splitIndex),
                    URLDecoder.decode(parameter.substring(splitIndex+1), "UTF-8"));
        }
        User user = new User(paramPair.get("userId"), paramPair.get("password"), paramPair.get("name"), paramPair.get("email"));
        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        builder.version(request.version())
                .statusCode(200)
                .body(user.toString().getBytes());

        return builder.build();
    }
}
