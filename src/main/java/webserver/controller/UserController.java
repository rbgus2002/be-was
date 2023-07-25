package webserver.controller;

import annotation.Controller;
import annotation.RequestMapping;
import db.Database;
import model.User;
import utils.Parser;
import webserver.http.request.HttpRequest;

import java.util.Map;

import static webserver.http.request.RequestMethod.POST;

@Controller
public class UserController {
    @RequestMapping(method = POST, value = "/user/create")
    public void saveUser(HttpRequest httpRequest) {
        Map<String, String> queryParams = Parser.parseQueryParameters(httpRequest.getHttpRequestBody());
        String userId = queryParams.get("userId");
        String password = queryParams.get("password");
        String name = queryParams.get("name");
        String email = queryParams.get("email");

        //TODO: 확장성을 고려했을 때 코드가 지저분해질 수 있다.
        if (userId == null || password == null || name == null || email == null) {
            throw new IllegalArgumentException("입력을 제대로 해주세요!");
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }
}
