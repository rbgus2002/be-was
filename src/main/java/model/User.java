package model;

import annotation.DataModel;
import annotation.DataModelField;

@DataModel(path = "/user/create")
public class User {

    @DataModelField
    private String userId;
    @DataModelField
    private String password;
    @DataModelField
    private String name;
    @DataModelField
    private String email;

    public User() {

    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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
