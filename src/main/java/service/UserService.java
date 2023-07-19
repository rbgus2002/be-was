package service;

import db.Database;
import model.User;

import java.util.Map;

import static model.User.*;
import static model.User.EMAIL;

public class UserService {

    public static void userSignUp(Map<String, String> queryParameterMap) throws NullPointerException {
        // User 객체 생성
        User user = new User(queryParameterMap.get(USERID),
                queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME),
                queryParameterMap.get(EMAIL));
        // DB 저장
        Database.addUser(user);
    }
}
