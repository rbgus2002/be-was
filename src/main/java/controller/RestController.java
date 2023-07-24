package controller;

import dto.LoginRequestDto;
import dto.UserFormRequestDto;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import model.enums.HttpStatusCode;
import model.enums.MIME;
import model.enums.Method;
import service.FileService;
import service.UserService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static constant.Uri.*;
import static mapper.ResponseMapper.*;
import static util.Authorization.needAuthorization;
import static util.Authorization.setSessionInCookie;
import static util.StringUtils.COMMA_MARK;
import static util.StringUtils.splitBy;

public class RestController {
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    private final FileService fileService;
    private final UserService userService;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
    }

    public HttpResponse route(HttpRequest httpRequest) {
        HttpResponse response = createNotFoundResponse(httpRequest);
        try {
            if (httpRequest.match(Method.POST, USER_CREATE_REQUEST_URI)) {
                response = addUserByForm(httpRequest);
            }

            if (httpRequest.match(Method.POST, USER_LOGIN_REQUEST_URI)) {
                response = loginUser(httpRequest);
            }

            if (isNotRestfulRequest(httpRequest)) {
                if (needAuthorization(httpRequest)) {

                }
                response = sendNotRestfulResponse(httpRequest);
            }
            return response;
        } catch (IOException e) {
            return createBadRequestResponse(httpRequest);
        }
    }

    private boolean needAuthorization(HttpRequest httpRequest) {
        return needAuthorization.contains(httpRequest.getUri());
    }

    private HttpResponse getUserList(HttpRequest httpRequest) {
        return null;
    }

    private HttpResponse loginUser(HttpRequest httpRequest) {
        Map<String, String> bodyMap = httpRequest.getBodyMap();
        LoginRequestDto dto = new LoginRequestDto(bodyMap.get(USER_ID), bodyMap.get(PASSWORD));
        Optional<User> loginUser = userService.login(dto);

        if (loginUser.isPresent()) {
            HttpResponse response = createRedirectResponse(httpRequest, HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
            setSessionInCookie(response, loginUser.get());
            return response;
        }
        return createRedirectResponse(httpRequest, HttpStatusCode.MOVED_PERMANENTLY, USER_LOGIN_FAILED_URI);
    }

    private HttpResponse addUserByForm(HttpRequest request) {
        Map<String, String> bodyMap = request.getBodyMap();
        userService.createByForm(new UserFormRequestDto(bodyMap));
        return createRedirectResponse(request, HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
    }

    private HttpResponse sendNotRestfulResponse(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        String[] extension = splitBy(uri, COMMA_MARK);

        int extensionIndex = extension.length - 1;
        MIME mime = MIME.valueOf(extension[extensionIndex].toUpperCase());
        return getHttpResponse(httpRequest, httpRequest.getUri(), mime);
    }

    private HttpResponse getHttpResponse(HttpRequest request, String path, MIME extension) throws IOException {
        byte[] fileContents = fileService.openFile(path, extension);
        return createHttpResponse(request, HttpStatusCode.OK, fileContents, extension);
    }

    private boolean isNotRestfulRequest(HttpRequest httpRequest) {
        return httpRequest.match(Method.GET) && httpRequest.isUriStaticFile();
    }
}
