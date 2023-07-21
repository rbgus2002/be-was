package model.factory;

import model.User;

import java.util.Map;

public class UserFactory {
    private UserFactory() {
    }

    public static User createUserFrom(Map<String, String> query){
        String userId = query.get("userId");
        String password = query.get("password");
        String name = query.get("name");
        String email = query.get("email");
        return new User(userId, password, name, email);
    }
}
