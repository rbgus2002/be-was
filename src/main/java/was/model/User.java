package was.model;

import was.utils.exception.ExceptionMessage;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        verifyUser(userId, password, name, email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void verifyUser(String userId, String password, String name, String email) {
       if (userId == null || userId.isBlank())
           throw new IllegalArgumentException(String.format(ExceptionMessage.USER_INFO_IS_NOT_EMPTY, "userId"));
       if (password == null || password.isBlank())
           throw new IllegalArgumentException(String.format(ExceptionMessage.USER_INFO_IS_NOT_EMPTY, "password"));
       if (name == null || name.isBlank())
           throw new IllegalArgumentException(String.format(ExceptionMessage.USER_INFO_IS_NOT_EMPTY, "name"));
       if (email == null || email.isBlank())
           throw new IllegalArgumentException(String.format(ExceptionMessage.USER_INFO_IS_NOT_EMPTY, "email"));
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
