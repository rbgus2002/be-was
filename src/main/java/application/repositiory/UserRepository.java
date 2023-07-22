package application.repositiory;

import application.dto.UserDto;
import db.Database;
import model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {

    public void addUser(UserDto userDto) {
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        Database.addUser(user);
    }

    public Optional<UserDto> findUserById(String userId) {
        User user = Database.findUserById(userId);

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

    public Collection<UserDto> findAll() {
        return Database.findAll().stream()
                .map(user -> new UserDto.Builder()
                        .withUserId(user.getUserId())
                        .withPassword(user.getPassword())
                        .withName(user.getName())
                        .withEmail(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}

