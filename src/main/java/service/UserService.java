package service;

import db.Database;
import dto.CreateUserRequestDto;
import dto.LoginRequestDto;
import model.User;

import java.util.Optional;

public class UserService {
    public static void createUser(CreateUserRequestDto dto) {
        Database.addUser(dto.toEntity());
    }

    public static Optional<User> login(LoginRequestDto dto) {
        Optional<User> findUser = Database.findUserById(dto.getUserId());
        if(findUser.isPresent() && findUser.get().checkPassword(dto.getPassword())) {
            return findUser;
        }
        return Optional.empty();
    }
}
