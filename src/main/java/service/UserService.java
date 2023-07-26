package service;

import db.Database;
import dto.LoginRequestDto;
import dto.UserFormRequestDto;
import model.User;

import java.util.Optional;

public class UserService {
    public void createByForm(UserFormRequestDto dto) {
        User userEntity = dto.toEntity();
        Database.addUser(userEntity);
    }

    public boolean login(LoginRequestDto dto) {
        Optional<User> result = Database.findUserById(dto.getUserId());
        return result.isPresent()
                && result.get().matchPassword(dto.getPassword());
    }
}
