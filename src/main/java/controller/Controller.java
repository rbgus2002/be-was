package controller;

import annotation.RequestMapping;
import common.http.HttpRequest;
import common.http.Queries;
import model.User;
import modelview.ModelView;
import service.UserService;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;

public class Controller {

    @RequestMapping(method = GET, path = "/")
    public ModelView root(HttpRequest request) {
        return new ModelView("Hello world");
    }

    @RequestMapping(method = GET, path = "/index.html")
    public ModelView index(HttpRequest request) {
        return new ModelView("/index.html");
    }

    @RequestMapping(method = GET, path = "/user/form.html")
    public ModelView signUpForm(HttpRequest request) {
        return new ModelView("/user/form.html");
    }

    @RequestMapping(method = POST, path = "/user/create")
    public ModelView createUser(HttpRequest request) {
        Queries queries = request.getQueries();

        User user = UserService.createUser(
                queries.getValue("userId"),
                queries.getValue("password"),
                queries.getValue("name"),
                queries.getValue("email")
        );

        ModelView mv = new ModelView("redirect:/index.html");
        mv.setAttribute("user", user);
        return mv;
    }

}
