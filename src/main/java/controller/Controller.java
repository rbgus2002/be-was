package controller;

import annotations.GetMapping;
import global.constant.StatusCode;
import global.response.ResponseEntity;
import model.UserParam;
import service.UserService;

import java.util.Map;

public class Controller {
    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public String root(Map<String, String> queryParams) {
        return ResponseEntity
                .responseBody("Hello world!")
                .build();
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
