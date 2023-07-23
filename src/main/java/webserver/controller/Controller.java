package webserver.controller;

import service.FileService;
import webserver.annotaion.Handler;
import webserver.http.HttpMethod;
import webserver.http.HttpStatusCode;
import webserver.http.MIME;
import webserver.http.request.HttpRequest;
import webserver.http.response.Body;
import webserver.http.response.HttpResponse;
import service.UserService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Controller {
    @Handler(method = HttpMethod.GET, path = "/user/create")
    public HttpResponse createUser(HttpRequest request) {
        UserService.addUser(request.createUserFromQuery());
        Body body = Body.from("회원가입 성공".getBytes(), MIME.TXT);
        return HttpResponse.of(request.getVersion(), HttpStatusCode.CREATED, body);
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
