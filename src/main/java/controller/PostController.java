package controller;

import db.Database;
import model.Post;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.exception.FoundException;
import support.web.HttpMethod;
import utils.LoginUtils;
import webserver.request.HttpRequest;

@Controller(value = "/post")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostViewController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/write")
    public String write(@RequestParam("title") String title,
            @RequestParam("writer") String writer,
            @RequestParam("contents") String contents,
            HttpRequest request) throws FoundException {
        logger.debug("쓰기 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession == null) {
            throw new FoundException("/user/login.html");
        }

        Post post = new Post(title, writer, contents);
        Database.addPost(post.getId(), post);

        logger.debug("게시글... 생성 됨... title: {} writer: {} contents: {} ", title, writer, contents);
        throw new FoundException("/index");
    }

}
