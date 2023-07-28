package service;

import global.util.SessionUtil;
import model.Session;
import model.db.Database;
import model.User;
import model.UserParam;

import java.util.Map;

import static model.db.Database.findUserById;

public class UserService {

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

    public String login(Map<String, String> headerParams, Map<String, String> bodyParams, SessionUtil sessionUtil) {
        String userId = bodyParams.get(UserParam.ID);
        Session session = new Session();
        session.setAttribute("userId", userId);

        String sid = sessionUtil.createSession();
        sessionUtil.setSession(sid, session);
        return sid;
    }
}
