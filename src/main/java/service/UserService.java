package service;

import db.Database;
import dto.UserFormRequestDto;
import model.User;

public class UserService {
    public void createByForm(UserFormRequestDto dto){
        User userEntity = dto.toEntity();
        Database.addUser(userEntity);
    }
}
