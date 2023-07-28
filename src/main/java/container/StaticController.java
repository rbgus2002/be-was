package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

public class StaticController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        String url = request.getUrl();
        switch (url) {
            case "/":
                return "redirect:/index.html";
            case "/user/login.html":
                return "redirect:/user/login";
            case "/user/list.html":
                return "redirect:/user/list";
            case "/user/form.html":
                return "redirect:/user/form";
            case "/user/profile.html":
                return "redirect:/user/profile";
            case  "/qna/form.html":
                return "redirect:/qna/form";
            default:
                return url;
        }
    }
}
