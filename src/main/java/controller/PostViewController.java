package controller;

import db.Database;
import model.Post;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.PathVariable;
import support.annotation.RequestMapping;
import support.exception.FoundException;
import support.web.HttpMethod;
import support.web.ModelAndView;
import utils.LoginUtils;
import webserver.request.HttpRequest;

@Controller(value = "/post")
public class PostViewController {

    private final Logger logger = LoggerFactory.getLogger(PostViewController.class);

    @RequestMapping(method = HttpMethod.GET, value = "/form")
    public ModelAndView form(HttpRequest request) throws FoundException {
        logger.debug("작성 폼 뷰 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/post/form.html");
            return modelAndView;
        }

        throw new FoundException("/user/login.html");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/show")
    public ModelAndView show(@PathVariable("id") String postId, HttpRequest request) throws FoundException {
        logger.debug("게시글 세부 글 뷰 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            Post post = Database.findPostById(Long.valueOf(postId));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/post/show");
            modelAndView.addAttribute("title", post.getTitle());
            modelAndView.addAttribute("writer", post.getWriter());
            modelAndView.addAttribute("createDateTime", post.getCreateDateTime());
            modelAndView.addAttribute("content", post.getContents());
            return modelAndView;
        }

        throw new FoundException("/user/login.html");
    }

}
