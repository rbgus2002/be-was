package controller;

import annotation.RequestMapping;
import common.enums.RequestMethod;
import common.http.HttpRequest;
import model.User;
import modelview.ModelView;
import service.UserService;

import java.util.Map;

public class Controller {

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ModelView root(HttpRequest request) {
        return new ModelView("Hello world");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/index.html")
    public ModelView index(HttpRequest request) {
        return new ModelView("/index.html");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/form.html")
    public ModelView signUpForm(HttpRequest request) {
        return new ModelView("/user/form.html");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/create")
    public ModelView createUser(HttpRequest request) {
        Map<String, String> params = request.getParams();
        User user = UserService.createUser(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );

        ModelView mv = new ModelView("OK");
        mv.setAttribute("user", user);
        return mv;
    }

}
