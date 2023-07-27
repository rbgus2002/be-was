package controller;

import annotation.Controller;
import annotation.RequestMapping;
import model.Model;
import model.User;
import session.SessionManager;
import http.request.HttpRequest;

import static http.request.RequestMethod.GET;

@Controller
public class HomeController {
    @RequestMapping(method = GET, value = "/index.html")
    public ModelAndView showHome(HttpRequest httpRequest) {
        if (httpRequest.getSessionId() == null) {
            return new ModelAndView("/index");
        }
        User user = SessionManager.getUser(httpRequest.getSessionId());
        if (user == null) {
            return new ModelAndView("/index");
        }

        Model model = new Model();
        model.addAttribute("user", user);

        return new ModelAndView("/index", model);
    }
}
