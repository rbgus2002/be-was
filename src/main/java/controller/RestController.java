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

import static constant.Uri.*;
import static mapper.ResponseMapper.*;
import static util.Authorization.NEEDED_AUTHORIZATION;
import static util.Authorization.setSessionInCookie;
import static util.StringUtils.COMMA_MARK;
import static util.StringUtils.splitBy;

public class RestController implements Controller {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    private final FileService fileService;
    private final UserService userService;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
    }

    private boolean needAuthorization(HttpRequest httpRequest) {
        return NEEDED_AUTHORIZATION.contains(httpRequest.getUri());
    }

    @GetMapping(path = "/index.html")
    public HttpResponse getHttpResponse(String path, Mime extension) throws IOException {
        byte[] fileContents = fileService.openFile(path, extension);
        return createHttpResponse(HttpStatusCode.OK, fileContents, extension);
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

    public HttpResponse sendNotRestfulResponse(HttpRequest httpRequest) throws IOException {
        if (needAuthorization(httpRequest)) {
            Optional<User> result = Authorization.getSession(httpRequest);
            if (result.isEmpty()) {
                return getHttpResponse(USER_LOGIN_URI, Mime.HTML);
            }
        }
        return getHttpResponse(httpRequest.getUri(), httpRequest.getMimeType());
    }
}
