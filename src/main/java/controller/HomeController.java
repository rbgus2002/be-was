package controller;

import annotation.Controller;
import annotation.RequestMapping;
import model.Model;
import model.User;
import session.UserSessionManager;
import http.request.HttpRequest;

import static http.request.RequestMethod.GET;

@Controller
public class HomeController {
    @RequestMapping(method = GET, value = "/index.html")
    public ModelAndView showHome(HttpRequest httpRequest) {
        User user = UserSessionManager.getUser(httpRequest.getSessionId());
        if (user == null) {
            return new ModelAndView("/index.html", null);
        }

        Model model = new Model();
        model.addAttribute("user", user);

        return new ModelAndView("/index.html", model);
    }
}
