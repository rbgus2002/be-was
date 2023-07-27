package application.service.dto;

public class LoginRequest {
    private final String userId;
    private final String password;

    public LoginRequest(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
