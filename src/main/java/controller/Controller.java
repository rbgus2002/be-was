package controller;

import annotation.RequestMapping;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.wrapper.Cookies;
import common.wrapper.Queries;
import model.User;
import modelview.ModelView;
import service.UserService;
import session.UserSessionManager;

import java.util.Optional;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;

public class Controller {

    @RequestMapping(method = GET, path = "/")
    public ModelView root(HttpRequest request, HttpResponse response) {
        return new ModelView("Hello world");
    }

    @RequestMapping(method = GET, path = "/index.html")
    public ModelView index(HttpRequest request, HttpResponse response) {
        return new ModelView("/index.html");
    }

    @RequestMapping(method = GET, path = "/user/form.html")
    public ModelView signUpForm(HttpRequest request, HttpResponse response) {
        return new ModelView("/user/form.html");
    }

    @RequestMapping(method = POST, path = "/user/create")
    public ModelView createUser(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        User user = UserService.createUser(
                queries.getValue("userId"),
                queries.getValue("password"),
                queries.getValue("name"),
                queries.getValue("email")
        );

        ModelView mv = new ModelView("redirect:/index.html");
        mv.addAttribute("user", user);
        return mv;
    }

    @RequestMapping(method = GET, path = "/user/login.html")
    public ModelView loginPage(HttpRequest request, HttpResponse response) {
        return new ModelView("/user/login.html");
    }

    @RequestMapping(method = GET, path = "/user/login_failed.html")
    public ModelView loginFailedPage(HttpRequest request, HttpResponse response) {
        return new ModelView("/user/login_failed.html");
    }

    @RequestMapping(method = POST, path = "/user/login")
    public ModelView login(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        Optional<User> user = UserService.login(
                queries.getValue("userId"),
                queries.getValue("password")
        );

        if (user.isEmpty()) {
            return new ModelView("redirect:/user/login_failed.html");
        }

        UserSessionManager.createSession(user.get(), response, "/");

        return new ModelView("redirect:/index.html");
    }
}
