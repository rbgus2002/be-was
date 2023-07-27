package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.exception.BadRequestException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.request.HttpRequest;
import webserver.session.UserSessionManager;

import static utils.StringUtils.getDecodedString;

public class LoginController implements Controller{
    private static LoginController loginController;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final int LOGIN_PARAMS_LENGTH = 2;
    private final int KEY_VALUE_PAIR_LENGTH = 2;
    private final int VALUE_INDEX = 1;
    private LoginController() {

    }

    public static LoginController getInstance() {
        if(loginController == null) {
            loginController = new LoginController();
        }
        return loginController;
    }

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        User tempUser = new User();
        verifyRequest(request, tempUser);

        User existedUser = Database.findUserById(tempUser.getUserId());

        if(existedUser == null || !existedUser.isCorrectPassword(tempUser.getPassword())) {
            response.setStatus(HttpResponseStatus.STATUS_302);
            response.setHeader("Location", "/user/login_failed.html");
            return;
        }

        String sessionId = UserSessionManager.getInstance().putSession(existedUser, response);
        response.setStatus(HttpResponseStatus.STATUS_302);
        response.setHeader("Location", "/index.html");
    }

    private void verifyRequest(HttpRequest request, User user) {
        String[] bodies = request.getBody().split("[&]");

        if(bodies.length != LOGIN_PARAMS_LENGTH) {
            throw new BadRequestException("파라미터의 개수가 잘못되었습니다!");
        }

        parseBody(bodies, user);
    }

    private void parseBody(String[] bodies, User user) {
        for (String body : bodies) {
            String[] param = body.split("[=]");

            if(param.length != KEY_VALUE_PAIR_LENGTH) {
                throw new BadRequestException("Body의 형식이 잘못되었습니다!");
            }

            setParams(param, user);
        }
    }

    private void setParams(String[] param, User user) {
        switch (param[0]) {
            case "userId":
                user.setUserId(getDecodedString(param[VALUE_INDEX]));
                break;
            case "password":
                user.setPassword(getDecodedString(param[VALUE_INDEX]));
                break;
            default:
                throw new BadRequestException("알맞은 key값을 넣어주세요!");
        }
    }

}
