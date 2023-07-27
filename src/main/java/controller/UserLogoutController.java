package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;

@RequestMapping(values = {"/user/logout"})
public class UserLogoutController implements HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("GET".equals(request.getMethod())) {
            return doGet(request, response);
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }

    private static String doGet(HttpRequest request, HttpResponse response) {
        if (!request.hasValidSession()) {
            response.setBadRequest();
            return "/error/400.html";
        }
        response.setHeader("Set-Cookie", "sid=" + request.getSession().getSid() + "; Path=/" + "; Max-Age=0;");
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index.html";
    }
}
