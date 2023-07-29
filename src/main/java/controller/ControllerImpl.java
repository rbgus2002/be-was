package controller;

import controller.annotation.GetMapping;
import controller.annotation.PostMapping;
import db.Database;
import dto.LoginRequestDto;
import dto.UserFormRequestDto;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import model.enums.HttpStatusCode;
import model.enums.Mime;
import service.FileService;
import service.UserService;
import session.Authorization;
import session.HttpSession;
import view.DynamicHtml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static constant.Uri.*;
import static mapper.ResponseMapper.createHttpResponse;
import static mapper.ResponseMapper.createRedirectResponse;
import static session.Authorization.setSessionInCookie;

public class ControllerImpl implements Controller {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    private final FileService fileService;
    private final UserService userService;

    public ControllerImpl() {
        fileService = new FileService();
        userService = new UserService();
    }

    @GetMapping
    public HttpResponse getHttpResponse(HttpRequest httpRequest) throws IOException {
        String path = httpRequest.getUri();
        Mime extension = Mime.getValueOf(path);

        byte[] fileContents = fileService.openFile(path, extension);
        return createHttpResponse(HttpStatusCode.OK, fileContents, extension);
    }

    @GetMapping(path = "/index.html")
    public HttpResponse getIndexHtml(HttpRequest httpRequest) throws IOException {
        String sessionId = httpRequest.getSessionIdInCookie();
        Optional<HttpSession> session = Authorization.getSession(sessionId);

        Map<String, Object> viewParameters = new HashMap<>();
        session.ifPresent(httpSession -> viewParameters.put("users", httpSession.getUserData()));

        byte[] content = DynamicHtml.select(httpRequest, viewParameters);
        return getHttpResponse(httpRequest.getUri(), content);
    }

    @GetMapping(path = "/user/list.html")
    public HttpResponse authorizeUserList(HttpRequest httpRequest) throws IOException {
        String sessionId = httpRequest.getSessionIdInCookie();
        Optional<HttpSession> session = Authorization.getSession(sessionId);
        if (session.isEmpty()) {
            return createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, USER_LOGIN_URI);
        }

        Map<String, Object> viewParameter = new HashMap<>();
        viewParameter.put("users", Database.findAllUser());

        byte[] contents = DynamicHtml.select(httpRequest, viewParameter);
        return getHttpResponse(httpRequest.getUri(), contents);
    }

    // FIXME step-7
    @GetMapping(path = "/qna/form.html")
    public HttpResponse write(HttpRequest httpRequest) throws IOException {
        String sessionId = httpRequest.getSessionIdInCookie();
        Optional<HttpSession> session = Authorization.getSession(sessionId);
        if (session.isEmpty()) {
            return createRedirectResponse(HttpStatusCode.MOVED_PERMANENTLY, USER_LOGIN_URI);
        }
        return getHttpResponse(httpRequest);
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

    private HttpResponse getHttpResponse(String path, byte[] body) throws IOException {
        Mime extension = Mime.getValueOf(path);
        return createHttpResponse(HttpStatusCode.OK, body, extension);
    }
}
