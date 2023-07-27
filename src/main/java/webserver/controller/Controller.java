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
            String sid = Session.addSession(user.get());
            response.setCookie(sid, "/");
            return response;
        }
        return HttpResponse.redirectResponse(request.getVersion(), "/user/login_failed.html");
    }

    @RequestMapping(method = HttpMethod.GET, path = "/user/list.html")
    public HttpResponse userList(HttpRequest request) {
        Object sessionUser = Session.get(request.getSid());
        if (sessionUser == null) {
            return HttpResponse.redirectResponse(request.getVersion(), "/user/login.html");
        }
        return getStaticResource(request);
    }

    public HttpResponse getStaticResource(HttpRequest request) {
        try {
            Object sessionUser = Session.get(request.getSid());
            byte[] content = FileService.getStaticResource(request.getPath(), (User) sessionUser);
            ResponseBody body = ResponseBody.from(content, request.getMime());
            return HttpResponse.of(request.getVersion(), HttpStatusCode.OK, body);
        } catch (NoSuchFileException exception) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.NOT_FOUND);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
