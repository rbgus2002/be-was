package application.service.dto;

public class UserRequest {
    private final String userId;

    private final String name;

    private final String password;

    private final String email;

    public UserRequest(final String userId, final String name, final String password, final String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
