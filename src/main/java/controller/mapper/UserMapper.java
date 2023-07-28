package controller.mapper;

import controller.dto.UserResponse;
import model.User;

import java.util.Map;

public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");
        return new User(userId, password, name, email);
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getUserId(), user.getName(), user.getEmail());
    }

}
