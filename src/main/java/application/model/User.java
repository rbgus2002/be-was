package application.model;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        verifyUser(userId);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void verifyUser(String userId) {
        if (userId.equals("")) {
            throw new IllegalArgumentException("유저 ID가 올바르지 않습니다.");
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
