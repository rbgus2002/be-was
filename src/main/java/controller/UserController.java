package controller;

import annotation.Controller;
import annotation.RequestMapping;
import db.Database;
import model.Model;
import model.User;
import session.SessionManager;
import utils.Parser;
import utils.StringUtils;
import http.request.HttpRequest;

import java.util.Map;

import static http.request.RequestMethod.GET;
import static http.request.RequestMethod.POST;

@Controller
public class UserController {
    @RequestMapping(method = POST, value = "/user/create")
    public ModelAndView saveUser(HttpRequest httpRequest) {
        Map<String, String> queryParams = Parser.parseQueryParameters(httpRequest.getHttpRequestBody());
        String userId = queryParams.get("userId");
        String password = queryParams.get("password");
        String name = queryParams.get("name");
        String email = queryParams.get("email");

        //TODO: 확장성을 고려했을 때 코드가 지저분해질 수 있다.
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(name) || StringUtils.isEmpty(email) ) {
            return new ModelAndView("redirect:/user/form.html");
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);

        return new ModelAndView("redirect:/index.html");
    }

    @RequestMapping(method = POST, value = "/user/login")
    public ModelAndView login(HttpRequest httpRequest) {
        String httpRequestBody = httpRequest.getHttpRequestBody();
        Map<String, String> paramMap = Parser.parseQueryParameters(httpRequestBody);

        if (!validateUser(paramMap.get("userId"), paramMap.get("password"))) {
            return new ModelAndView("redirect:/user/login_failed.html");
        }

        User user = Database.findUserById(paramMap.get("userId"));
        Model model = new Model();
        model.addAttribute("user", user);
        return new ModelAndView("redirect:/index.html", model);
    }

    @RequestMapping(method = GET, value = "/user/list.html")
    public ModelAndView list(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        sessionId = Parser.parseRequestCookie(sessionId);

        if (sessionId.isEmpty()) {
            return new ModelAndView("redirect:/user/login.html");
        }

        User user = SessionManager.getUser(sessionId);
        Model model = new Model();
        model.addAttribute("user", user);
        return new ModelAndView("/list", model);
    }

    private boolean validateUser(String userId, String password) {
        User user = Database.findUserById(userId);
        return user.getPassword().equals(password);
    }

}
