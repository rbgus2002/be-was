package user.controller;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.handler.request.annotation.RequestMapping;
import webserver.myframework.model.Model;
import webserver.myframework.session.Session;

@SuppressWarnings("unused")
@Controller
public class StaticController {
    @RequestMapping("/index.html")
    public void index(HttpRequest httpRequest, HttpResponse httpResponse, Model model) {
        Session session = httpRequest.getSession(false);
        if(session != null) {
            User user = Database.findUserById((String) session.getAttribute("userId"));
            model.addParameter("user", user);
        }
        httpResponse.setUri("/index.html");
    }
}
