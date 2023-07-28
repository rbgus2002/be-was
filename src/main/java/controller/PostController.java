package controller;

import db.Database;
import model.Post;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.web.HttpMethod;
import support.web.ResponseEntity;
import utils.LoginUtils;
import webserver.Header;
import webserver.request.HttpRequest;
import webserver.response.HttpStatus;

@Controller(value = "/post")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostViewController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/write")
    public ResponseEntity<?> write(@RequestParam("title") String title,
                                   @RequestParam("writer") String writer,
                                   @RequestParam("contents") String contents,
                                   HttpRequest request) {
        logger.debug("쓰기 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession == null) {
            Header header = new Header();
            header.setLocation("/user/login.html");
            return new ResponseEntity<>(HttpStatus.FOUND, header);
        }

        Post post = new Post(title, writer, contents);
        Database.addPost(post.getId(), post);

        logger.debug("게시글 생성! title: {} writer: {} contents: {} ", title, writer, contents);
        Header header = new Header();
        header.setLocation("/");
        return new ResponseEntity<>(HttpStatus.FOUND, header);
    }

}
