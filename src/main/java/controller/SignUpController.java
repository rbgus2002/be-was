package controller;

import Service.UserService;
import annotation.Controller;
import annotation.RequestMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.server.DispatcherServlet;

import java.util.Map;

import static model.User.*;

@Controller
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);


    @RequestMapping(path = "/user/create")
    public void signUp(HttpRequest request, HttpResponse response) {
        UserService userService = new UserService();
        Map<String, String> queries = request.getQueries();
        if(request.getMethod() == HttpMethod.POST) {
            queries = request.getBodies();
        }
        User user = new User(queries.get(USERID_KEY),
                queries.get(PASSWORD_KEY),
                queries.get(NAME_KEY),
                queries.get(EMAIL_KEY));

        userService.save(user);
        response.setToUrl(DispatcherServlet.REDIRECT + "/index.html");
        logger.info("signup ok, userId : {}", queries.get(USERID_KEY));
    }


}
