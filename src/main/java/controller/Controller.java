package controller;

import annotations.GetMapping;
import annotations.PostMapping;
import controller.dto.UserResponse;
import controller.mapper.UserMapper;
import global.constant.Headers;
import global.constant.StatusCode;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.response.ResponseEntity;
import global.util.SessionUtil;
import model.db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import view.View;
import webserver.WebServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private final static UserService userService = new UserService();
    private final static View view = new View();
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping(path = "/index.html")
    public byte[] root(RequestHeader header, RequestBody body, SessionUtil sessionUtil) throws IOException {
        Map<String, Object> viewParameters = new HashMap<>();
        viewParameters.put("view", "index");
        try {
            String userId = (String) sessionUtil.getSession(header.get(Headers.COOKIE.getKey())).getAttribute("userId");
            viewParameters.put("name", userId);
        } catch (Exception e) {
            logger.warn("세션이 존재하지 않습니다.");
        }

        return ResponseEntity
                .statusCode(StatusCode.OK)
                .responseBody(view.index(viewParameters))
                .build();
    }

    @GetMapping(path = "/user/list.html")
    public byte[] getUserList(RequestHeader header, RequestBody body, SessionUtil sessionUtil) throws IOException {
        Map<String, Object> viewParameters = new HashMap<>();
        List<UserResponse> userResponses = Database.findAll().stream().map(UserMapper::toResponse).collect(Collectors.toList());

        viewParameters.put("view", "list");
        viewParameters.put("users", userResponses);

        return ResponseEntity
                .statusCode(StatusCode.OK)
                .responseBody(view.list(viewParameters))
                .build();
    }

    @PostMapping(path = "/user/create")
    public byte[] createUser(RequestHeader header, RequestBody body, SessionUtil sessionUtil) throws IOException {
        Map<String, String> params = body.getParams();
        userService.register(params);
        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();
    }

    @PostMapping(path = "/user/login")
    public byte[] userLogin(RequestHeader header, RequestBody body, SessionUtil sessionUtil) throws IOException {
        Map<String, String> headerParams = header.getHeaders();
        Map<String, String> bodyParams = body.getParams();

        if (!userService.existUser(bodyParams)) {
            return ResponseEntity
                    .statusCode(StatusCode.FOUND)
                    .addHeaders(Headers.LOCATION, "/user/login_failed.html")
                    .responseResource("/user/login_failed.html")
                    .build();
        }

        String sid = userService.login(headerParams, bodyParams, sessionUtil);

        return ResponseEntity
                .statusCode(StatusCode.FOUND)
                .setCookie(sid, "/")
                .addHeaders(Headers.LOCATION, "/index.html")
                .responseResource("/index.html")
                .build();

    }
}
