package controller;

import annotations.GetMapping;
import annotations.PostMapping;
import global.constant.Headers;
import global.constant.StatusCode;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.response.ResponseEntity;
import model.Session;
import service.UserService;

import java.io.IOException;
import java.util.Map;

public class Controller {
    private final static UserService userService = new UserService();

    @GetMapping(path = "/")
    public byte[] root(RequestHeader header, RequestBody body, Session session) throws IOException {
        return ResponseEntity
                .statusCode(StatusCode.OK)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }

    @PostMapping(path = "/user/create")
    public byte[] createUser(RequestHeader header, RequestBody body, Session session) throws IOException {
        Map<String, String> params = body.getParams();
        userService.register(params);
        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }

    @PostMapping(path = "/user/login")
    public byte[] userLogin(RequestHeader header, RequestBody body, Session session) throws IOException {
        Map<String, String> headerParams = header.getHeaders();
        Map<String, String> bodyParams = body.getParams();

        if (!userService.existUser(bodyParams)) {
            return ResponseEntity
                    .statusCode(StatusCode.FOUND)
                    .addHeaders(Headers.LOCATION, "/user/login_failed.html")
                    .responseResource("/user/login_failed.html")
                    .build();
        }

        String sid = userService.login(headerParams, bodyParams, session);

        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .setCookie(sid, "/")
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();

    }
}
