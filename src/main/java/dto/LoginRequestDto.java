package dto;

import java.util.Map;

public class LoginRequestDto {
    private String userId;
    private String password;

    public LoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public LoginRequestDto(Map<String, String> params) {
        this.userId = params.get("userId");
        this.password = params.get("password");
        if (hasMissingValue()) {
            throw new IllegalArgumentException("모든 필드값이 존재해야 합니다!");
        }
    }

    private boolean hasMissingValue() {
        return this.userId == null || this.password == null;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
