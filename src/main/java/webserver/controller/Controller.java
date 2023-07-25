package webserver.controller;

import service.FileService;
import webserver.annotaion.RequestMapping;
import webserver.http.HttpMethod;
import webserver.http.HttpStatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.Body;
import webserver.http.response.HttpResponse;
import service.UserService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Controller {
    private static final Controller controller = new Controller();

    private Controller() {
    }

    public static Controller getInstance() {
        return controller;
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse createUser(HttpRequest request) {
        UserService.addUser(request.createUserFromBody());
        return HttpResponse.redirectResponse(request.getVersion(), "/index.html");
    }

    public HttpResponse getStaticResource(HttpRequest request) {
        try {
            Body body = Body.from(FileService.getStaticResource(request.getPath()), request.getMime());
            return HttpResponse.of(request.getVersion(), HttpStatusCode.OK, body);
        } catch (NoSuchFileException exception) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.NOT_FOUND);
        } catch (IOException e) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
