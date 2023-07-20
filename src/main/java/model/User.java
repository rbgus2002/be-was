package model;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        verifyUser();
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void verifyUser() {
       if (userId == null || userId.isBlank())
           throw new IllegalArgumentException("User의 ID는 null이 될 수 없습니다");
       if (password == null || password.isBlank())
           throw new IllegalArgumentException("Password는 값이 비어있을 수 없습니다.");
       if (name == null || name.isBlank())
           throw new IllegalArgumentException("name은 값이 비어 있을 수 없습니다.");
       if (email == null || email.isBlank())
           throw new IllegalArgumentException("email은 값이 비어 있을 수 없습니다.");
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
