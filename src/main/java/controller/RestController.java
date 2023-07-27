package controller;

import controller.annotation.GetMapping;
import controller.annotation.PostMapping;
import dto.LoginRequestDto;
import dto.UserFormRequestDto;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import model.enums.HttpStatusCode;
import model.enums.Mime;
import service.FileService;
import service.UserService;
import util.Authorization;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static constant.Uri.INDEX_HTML_URI;
import static constant.Uri.USER_LOGIN_FAILED_URI;
import static mapper.ResponseMapper.createHttpResponse;
import static mapper.ResponseMapper.createRedirectResponse;
import static util.Authorization.setSessionInCookie;

public class RestController implements Controller {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    private final FileService fileService;
    private final UserService userService;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
    }

    @GetMapping(path = "/index.html")
    public HttpResponse getIndexHtml(HttpRequest httpRequest) throws IOException {
        return getHttpResponse(httpRequest.getUri());
    }

    @GetMapping(path = "/user/list.html")
    public HttpResponse authorizeUserList(HttpRequest httpRequest) throws IOException {
        Optional<User> session = Authorization.getSession(httpRequest);
        if (session.isEmpty()) {
            return createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
        }
        return getHttpResponse(httpRequest.getUri());
    }

    @PostMapping(path = "/user/login")
    public HttpResponse loginUser(Map<String, String> body) {
        LoginRequestDto dto = new LoginRequestDto(body.get(USER_ID), body.get(PASSWORD));
        Optional<User> loginUser = userService.login(dto);

        if (loginUser.isPresent()) {
            HttpResponse response = createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
            setSessionInCookie(response, loginUser.get());
            return response;
        }
        return createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, USER_LOGIN_FAILED_URI);
    }

    @PostMapping(path = "/user/create")
    public HttpResponse addUserByForm(Map<String, String> body) {
        userService.createByForm(new UserFormRequestDto(body));
        return createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
    }

    private HttpResponse getHttpResponse(String path) throws IOException {
        Mime extension = Mime.getValueOf(path);
        byte[] fileContents = fileService.openFile(path, extension);
        return createHttpResponse(HttpStatusCode.OK, fileContents, extension);
    }
}
