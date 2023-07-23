package application.repositiory;

import application.dto.UserDto;
import db.Database;
import model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum UserRepository {

    USER_REPOSITORY(Database.INSTANCE);

    private final Database database;

    private UserRepository(Database database) {
        this.database = database;
    }

    public void addUser(UserDto userDto) {
        User user = User.of(userDto);
        database.addUser(user);
    }

    public Optional<UserDto> findUserById(String userId) {
        User user = database.findUserById(userId);

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(new UserDto.Builder()
                .withUserId(user.getUserId())
                .withPassword(user.getPassword())
                .withName(user.getName())
                .withEmail(user.getEmail())
                .build());
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

