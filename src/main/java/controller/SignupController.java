package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.SessionStorage;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.server.DispatcherServlet;

import java.util.Map;

import static model.User.*;

public class SignupController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);



    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) {
        Map<String, String> queries = request.getQueries();
        if(request.getMethod() == HttpMethod.POST) {
            queries = request.getBodies();
        }
        String userId = queries.get(USERID_KEY);
        User user = new User(queries.get(USERID_KEY),
                queries.get(PASSWORD_KEY),
                queries.get(NAME_KEY),
                queries.get(EMAIL_KEY));

        Database.addUser(user);

        String sessionId = SessionStorage.setSession(userId);
        response.setCookie(sessionId);
        response.setToUrl(DispatcherServlet.REDIRECT + "/index.html");
        logger.info("signup ok, userId : {}", queries.get(USERID_KEY));

        return response;
    }
}
