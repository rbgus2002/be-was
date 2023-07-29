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

    public Optional<User> login(LoginRequestDto dto) {
        Optional<User> result = Database.findUserById(dto.getUserId());
        if(result.isPresent() && result.get().matchPassword(dto.getPassword())){
            return result;
        }
        return Optional.empty();
    }
}
