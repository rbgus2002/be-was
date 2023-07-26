package dto;

import model.User;

import java.util.Map;

public class CreateUserRequestDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public CreateUserRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public CreateUserRequestDto(Map<String, String> params) {
        this.userId = params.get("userId");
        this.password = params.get("password");
        this.name = params.get("name");
        this.email = params.get("email");
        if (hasMissingValue()) {
            throw new IllegalArgumentException("모든 필드값이 존재해야 합니다!");
        }
    }

    private boolean hasMissingValue() {
        return this.userId == null || this.password == null || this.name == null || this.email == null;
    }

    public User toEntity() {
        return new User(
                this.userId,
                this.password,
                this.name,
                this.email
        );
    }
}
