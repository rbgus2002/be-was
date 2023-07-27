package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;

@RequestMapping(values = {"/user/logout"})
public class UserLogoutController implements HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        response.setHeader("Set-Cookie", "sid=" + request.getSession().getSid() + "; Path=/" + "; Max-Age=0;");
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index.html";
    }
}
