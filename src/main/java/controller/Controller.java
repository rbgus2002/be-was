package controller;

import annotations.GetMapping;
import annotations.PostMapping;
import global.constant.Headers;
import global.constant.StatusCode;
import global.request.RequestBody;
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
    public byte[] createUser(RequestBody body) throws IOException {
        Map<String, String> params = body.getParams();
        userService.register(params);
        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }
}
