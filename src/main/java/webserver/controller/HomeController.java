package webserver.controller;

import annotation.Controller;
import annotation.RequestMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.UserSessionManager;
import webserver.ModelAndView;
import webserver.http.request.HttpRequest;

import static webserver.http.request.RequestMethod.GET;

@Controller
public class HomeController {
    @RequestMapping(method = GET, value = "/index.html")
        public ModelAndView showHome(HttpRequest httpRequest) {
            User user = UserSessionManager.getUser(httpRequest.getSessionId());
            if (user == null) {
                return new ModelAndView("/index.html", null);
            }

            return new ModelAndView("/index.html", user);
    }
}
