package container;

import annotation.RequestMapping;
import db.ContentDatabase;
import db.SessionManager;
import model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.GetParser;
import util.PathList;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;

import static util.PathList.*;

@RequestMapping(path = "/qna/form")
public class WriteController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(WriteController.class);
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (request.getMethod().equals("POST")) {
            Map<String, String> query = request.getQuery();
            logger.debug("query의 생성 ={}", query.toString());
            Content content = new Content(query.get("writer"), query.get("title"), query.get("contents"));
            ContentDatabase.addContent(content);
            return HOME_PATH.getPath();
        }

        if (isLogInStatus(request)) {
           return "/qna/form.html";
        }
        return "/user/login.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        return SessionManager.getSession(request) != null;
    }
}
