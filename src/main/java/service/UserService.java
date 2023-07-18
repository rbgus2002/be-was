package service;

import db.Database;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static model.User.*;
import static model.User.EMAIL;

public class UserService {

    public static User getUser(String userId) {
        return Database.findUserById(userId);
    }
    public static Collection<User> getAllUser() {
        return Database.findAllUser();
    }

    public static void userSignUp(Map<String, String> parameterMap) throws NullPointerException {
        // User 객체 생성
        User user = new User(parameterMap.get(USERID),
                parameterMap.get(PASSWORD),
                parameterMap.get(NAME),
                parameterMap.get(EMAIL));
        // DB 저장
        Database.addUser(user);
    }

    public static boolean validateUser(String userId, String password) {
        // User 검색
        User user = Database.findUserById(userId);
        if (user == null) {
            return false;
        }

        // 아이디/비밀번호 검증
        return userId.equals(user.getUserId()) && password.equals(user.getPassword());
    }
}
