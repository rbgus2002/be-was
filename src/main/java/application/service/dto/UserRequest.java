package application.service.dto;

public class UserRequest {
    private final String id;

    private final String name;

    private final String password;

    private final String email;

    public UserRequest(final String id, final String name, final String password, final String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
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
