package controller;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.exception.FoundException;
import support.web.HttpMethod;
import utils.LoginUtils;
import webserver.request.HttpRequest;

@Controller(value = "/post")
public class PostViewController {

    private final Logger logger = LoggerFactory.getLogger(PostViewController.class);

    @RequestMapping(method = HttpMethod.GET, value = "/form")
    public String form(HttpRequest request) throws FoundException {
        logger.debug("작성 폼 뷰 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            return "/post/form.html";
        }

        throw new FoundException("/user/login.html");
    }

}
