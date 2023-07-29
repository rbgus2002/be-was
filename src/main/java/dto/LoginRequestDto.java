package dto;

import java.util.Map;

public class LoginRequestDto {
    private String userId;
    private String password;

    public LoginRequestDto(String id, String password) {
        this.userId = id;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
