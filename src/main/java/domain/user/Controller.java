package domain.user;

import annotation.RequestMapping;
import annotation.TemplateMapping;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.wrapper.Queries;
import template.*;
import webserver.ModelView;
import webserver.UserSessionManager;

import java.util.Optional;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;

public class Controller {

    @RequestMapping(method = GET, path = "/")
    public ModelView root(HttpRequest request, HttpResponse response) {
        return new ModelView("Hello world");
    }

    @TemplateMapping(name = DynamicTemplate.class)
    @RequestMapping(method = GET, path = "/index.html")
    public ModelView index(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/index.html");
        addUserToModelIfExists(mv, request);
        return mv;
    }

    @TemplateMapping(name = DynamicTemplate.class)
    @RequestMapping(method = GET, path = "/user/form.html")
    public ModelView userForm(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/form.html");
        addUserToModelIfExists(mv, request);
        return mv;
    }

    @TemplateMapping(name = DynamicTemplate.class)
    @RequestMapping(method = GET, path = "/user/login.html")
    public ModelView userLoginGet(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/login.html");
        addUserToModelIfExists(mv, request);
        return mv;
    }

    @RequestMapping(method = POST, path = "/user/create")
    public ModelView userCreate(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        User user = UserService.createUser(
                queries.getValue("userId"),
                queries.getValue("password"),
                queries.getValue("name"),
                queries.getValue("email")
        );

        ModelView mv = new ModelView("redirect:/index.html");
        mv.addModelAttribute("user", user);
        return mv;
    }

    @RequestMapping(method = POST, path = "/user/login")
    public ModelView userLoginPost(HttpRequest request, HttpResponse response) {
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

    @TemplateMapping(name = DynamicTemplate.class)
    @RequestMapping(method = GET, path = "/user/login_failed.html")
    public ModelView userLoginFailed(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/login_failed.html");
        addUserToModelIfExists(mv, request);
        return mv;
    }

    @TemplateMapping(name = UserListTemplate.class)
    @RequestMapping(method = GET, path = "/user/list.html")
    public ModelView listUser(HttpRequest request, HttpResponse response) {
        User user = UserSessionManager.getSession(request);

        if (user != null) {
            ModelView mv = new ModelView("/user/list.html");
            mv.addModelAttribute("user", user);
            mv.addModelAttribute("users", UserService.getAllUsers());

            return mv;
        }

        return new ModelView("redirect:/index.html");
    }

    private void addUserToModelIfExists(ModelView mv, HttpRequest request) {
        User user = UserSessionManager.getSession(request);
        if (user != null) {
            mv.addModelAttribute("user", user);
        }
    }
}
