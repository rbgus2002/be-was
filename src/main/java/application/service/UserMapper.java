package application.service;

import application.model.User;
import application.service.dto.UserRequest;

public class UserMapper {
    public User userFrom(UserRequest userRequest) {
        return new User(userRequest.getUserId(), userRequest.getPassword(), userRequest.getName(), userRequest.getEmail());
    }
}
