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

    public static boolean login(LoginRequestDto dto) {
        Optional<User> findUser = Database.findUserById(dto.getUserId());
        return findUser.isPresent() && findUser.get().checkPassword(dto.getPassword());
    }
}
