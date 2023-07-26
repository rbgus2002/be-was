package controller;

import annotations.GetMapping;
import annotations.PostMapping;
import global.constant.Headers;
import global.constant.StatusCode;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.response.ResponseEntity;
import service.UserService;

import java.io.IOException;
import java.util.Map;

public class Controller {
    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public byte[] root(Map<String, String> queryParams) throws IOException {
        return ResponseEntity
                .statusCode(StatusCode.OK)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }

    @PostMapping(path = "/user/create")
    public byte[] createUser(RequestHeader header, RequestBody body) throws IOException {
        Map<String, String> params = body.getParams();
        userService.register(params);
        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }

    @PostMapping(path = "/user/login")
    public byte[] userLogin(RequestHeader header, RequestBody body) throws IOException {
        Map<String, String> headerParams = header.getHeaders();
        Map<String, String> bodyParams = body.getParams();

        if (!userService.existUser(bodyParams)) {
            return ResponseEntity
                    .statusCode(StatusCode.FOUND)
                    .addHeaders(Headers.LOCATION, "/user/login_failed.html")
                    .responseResource("/user/login_failed.html")
                    .build();
        }

        userService.login(headerParams, bodyParams);
        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .setCookie(userService.getUserSession(bodyParams), "/")
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();

    }
}
