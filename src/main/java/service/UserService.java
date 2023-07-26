package service;

import global.constant.Headers;
import global.util.SessionUtil;
import model.db.Database;
import model.User;
import model.UserParam;

import java.util.Map;

import static model.db.Database.findUserById;

public class UserService {
    private final SessionUtil sessionUtil = new SessionUtil();

    public void register(Map<String, String> params) {
        final User user = new User(
                params.get(UserParam.ID),
                params.get(UserParam.PASSWORD),
                params.get(UserParam.NAME),
                params.get(UserParam.EMAIL));
        Database.addUser(user);
    }

    public boolean existUser(Map<String, String> params) {
        try {
            User user = findUserById(params.get(UserParam.ID));
            return user.checkPassword(params.get(UserParam.PASSWORD));
        } catch (Exception e) {
            return false;
        }
    }

    public void login(Map<String, String> headerParams, Map<String, String> bodyParams) {
        sessionUtil.setSession(headerParams.get(Headers.COOKIE.getKey()), findUserById(bodyParams.get(UserParam.ID)));
    }

    public String getUserSession(Map<String, String> bodyParams) {
        User user = findUserById(bodyParams.get(UserParam.ID));
        return sessionUtil.getSessionByUser(user);
    }
}
