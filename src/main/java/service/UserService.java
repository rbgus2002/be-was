package service;

import db.Database;
import dto.UserDTO;
import model.User;

public class UserService {

    private final Database database = new Database();

    public void createUser(UserDTO userDTO) {
        User user = userMapper(userDTO);


    }

    private User userMapper(UserDTO userDTO) {
        return new User(userDTO.getUserId(), userDTO.getPassword(), userDTO.getName(), userDTO.getEmail());
    }
}
