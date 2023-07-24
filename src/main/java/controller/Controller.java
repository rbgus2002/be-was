package controller;

import annotations.GetMapping;
import annotations.PostMapping;
import global.constant.StatusCode;
import global.request.RequestBody;
import global.response.ResponseEntity;
import model.UserParam;
import service.UserService;

import java.util.Map;

public class Controller {
    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public byte[] root(Map<String, String> queryParams) {
        return ResponseEntity
                .responseBody("Hello world!".getBytes())
                .build();
    }

    @PostMapping(path = "/user/create")
    public byte[] createUser(RequestBody body) {
        System.out.println(body.getBody());
        Map<String, String> params = body.getParams();
        userService.register(params);
        return ResponseEntity
                .statusCode(StatusCode.OK)
                .responseBody(("Created User: " + params.get(UserParam.EMAIL)).getBytes())
                .build();
    }
}
