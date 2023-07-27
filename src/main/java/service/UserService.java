package service;

import db.Database;
import model.User;

import java.util.Collection;
import java.util.Map;

import static model.User.*;

public class UserService {

    public static User getUser(String userId) {
        return Database.findUserById(userId);
    }
    public static Collection<User> getAllUser() {
        return Database.findAllUser();
    }

    public static void userSignup(String userId, String password, String name, String email) {
        // 이미 존재하는 User인지 확인
        User user = Database.findUserById(userId);
        if(user != null) {
            return;
        }
        // User 객체 생성
        user = new User(userId, password, name, email);
        // DB 저장
        Database.addUser(user);
    }

    public static boolean validateUser(String userId, String password) {
        // User 검색
        User user = Database.findUserById(userId);
        if (user == null) {
            return false;
        }

        // 비밀번호 검증
        return password.equals(user.getPassword());
    }

    public static void clearUserDatabase() {
        Database.deleteAllUser();
    }
}
