package controller;

import annotation.RequestMapping;
import db.UserRepository;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "/user/create")
public class UserJoinController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        User user = User.fromMap(request.getParams());
        UserRepository.addUser(user);
        return "redirect:/index.html";
    }

}
