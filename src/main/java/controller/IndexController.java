package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.handler.request.annotation.RequestMapping;

@SuppressWarnings("SameReturnValue")
@Controller
public class IndexController {
    @RequestMapping("/index.html")
    public String frontPage(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
