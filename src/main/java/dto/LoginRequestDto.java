package dto;

import java.util.Map;

public class LoginRequestDto {
    private String userId;
    private String password;

    public LoginRequestDto(Map<String, String> map) {
        this.userId = map.get("userId");
        this.password = map.get("password");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
