package dto;

import model.User;

import java.util.Map;

public class UserFormRequestDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserFormRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // 나중에 지워짐
    public UserFormRequestDto(Map<String, String> params) {
        for (var entry : params.entrySet()) {
            var key = entry.getKey();
            var val = (String) entry.getValue();
            switch (key) {
                case "userId":
                    this.userId = val;
                    break;
                case "password":
                    this.password = val;
                    break;
                case "name":
                    this.name = val;
                    break;
                case "email":
                    this.email = val;
                    break;
            }
        }
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
