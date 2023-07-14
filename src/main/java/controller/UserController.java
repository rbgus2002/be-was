package controller;

import service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static http.Body.loadTemplatesFromPath;

public class UserController {
    private final UserService userService = new UserService();

    public byte[] createUser(String uri) {
        String[] params = uri.split("&");
        Map<String, String> userInfo = new HashMap<>();

        for (String param : params) {
            String[] info = param.split("=");
            userInfo.put(info[0], info[1]);
        }

        try {
            userService.createUser(userInfo);
            return loadTemplatesFromPath("/user/signup_success.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
