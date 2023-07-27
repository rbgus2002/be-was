package dto;

import model.User;

public class UserResponseDto {
    private String userId;
    private String name;
    private String email;

    private UserResponseDto(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUserId(), user.getName(), user.getEmail());
    }

    public String getName() {
        return this.name;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getEmail() {
        return this.email;
    }
}
