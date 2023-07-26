package webserver.controller;

import dto.CreateUserRequestDto;
import dto.LoginRequestDto;
import model.User;
import service.FileService;
import webserver.annotaion.RequestMapping;
import webserver.http.HttpMethod;
import webserver.http.HttpStatusCode;
import webserver.http.Session;
import webserver.http.request.HttpRequest;
import webserver.http.response.ResponseBody;
import webserver.http.response.HttpResponse;
import service.UserService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Optional;
import java.util.UUID;

public class Controller {
    private static final Controller controller = new Controller();

    private Controller() {
    }

    public static Controller getInstance() {
        return controller;
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse createUser(HttpRequest request) {
        UserService.createUser(new CreateUserRequestDto(request.getParams()));
        return HttpResponse.redirectResponse(request.getVersion(), "/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/login")
    public HttpResponse login(HttpRequest request) {
        Optional<User> user = UserService.login(new LoginRequestDto(request.getParams()));
        if (user.isPresent()) {
            HttpResponse response = HttpResponse.redirectResponse(request.getVersion(), "/index.html");
            String sessionKey = UUID.randomUUID().toString();
            Session.put(sessionKey, user.get());
            response.setCookie(sessionKey, "/");
            return response;
        }
        return HttpResponse.redirectResponse(request.getVersion(), "/user/login_failed.html");
    }

    public HttpResponse getStaticResource(HttpRequest request) {
        try {
            ResponseBody body = ResponseBody.from(FileService.getStaticResource(request.getPath()), request.getMime());
            return HttpResponse.of(request.getVersion(), HttpStatusCode.OK, body);
        } catch (NoSuchFileException exception) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.NOT_FOUND);
        } catch (IOException e) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
