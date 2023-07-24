package application.repositiory;

import application.dto.UserDto;
import db.Database;
import exception.InvalidQueryParameterException;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

public enum UserRepository {

    USER_REPOSITORY(Database.INSTANCE);

    private final Database database;

    UserRepository(Database database) {
        this.database = database;
    }

    public void addUser(final UserDto userDto) {
        User user = User.of(userDto);
        database.addUser(user);
    }

    public UserDto findUserById(final String userId) {
        User user = database.findUserById(userId);

        if (user == null) throw new InvalidQueryParameterException();

        return new UserDto.Builder()
                .withUserId(user.getUserId())
                .withPassword(user.getPassword())
                .withName(user.getName())
                .withEmail(user.getEmail())
                .build();
    }

    public List<UserDto> findAll() {
        return database.findAll().stream()
                .map(user -> new UserDto.Builder()
                        .withUserId(user.getUserId())
                        .withPassword(user.getPassword())
                        .withName(user.getName())
                        .withEmail(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}

