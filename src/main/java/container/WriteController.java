package container;

import annotation.RequestMapping;
import db.ContentDatabase;
import db.SessionManager;
import model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;

import static util.PathList.HOME_PATH;

@RequestMapping(path = "/qna/form")
public class WriteController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(WriteController.class);
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (isPost(request)) {
            Map<String, String> query = request.getQuery();
            logger.debug("query의 생성 ={}", query.toString());
            Content content = new Content(query.get("writer"), query.get("title"), query.get("contents"));
            logger.debug("content의 시간 = {}", content.getLocalDate());
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

    private boolean isPost(HTTPServletRequest request) {
        return request.getMethod().equals("POST");
    }
}
