package controller;

import annotations.GetMapping;
import exception.BadRequestException;
import global.constant.Headers;
import global.constant.StatusCode;
import global.response.ResponseEntity;
import model.UserParam;
import service.UserService;

import java.io.IOException;
import java.util.Map;

public class Controller {
    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public String root(Map<String, String> queryParams) {
        return ResponseEntity
                .responseBody("Hello world!")
                .build();
    }

    @GetMapping(path = "/index.html")
    public String getIndexHtml(Map<String, String> queryParams) {
        try {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, "/index.html")
                    .responseResource("/index.html")
                    .build();
        } catch (IOException e) {
            throw new BadRequestException();
        }
    }

    @GetMapping(path = "/user/form.html")
    public String getFormHtml(Map<String, String> queryParams) {
        try {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, "/user/form.html")
                    .responseResource("/user/form.html")
                    .build();
        } catch (IOException e) {
            throw new BadRequestException();
        }
    }

    @GetMapping(path = "/user/create")
    public String createUser(Map<String, String> queryParams) {
        userService.register(queryParams);
        return ResponseEntity
                .statusCode(StatusCode.OK)
                .responseBody("Created User: " + queryParams.get(UserParam.EMAIL))
                .build();
    }
}
