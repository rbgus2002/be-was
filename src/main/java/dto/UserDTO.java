package dto;

public class UserDTO {

    private String userId;
    private String password;
    private String name;
    private String email;

    public static class builder {

        private String userId;
        private String password;
        private String name;
        private String email;

        public builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public builder password(String password) {
            this.password = password;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public builder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(userId, password, name, email);
        }
    }

    public UserDTO(String userId, String password, String name, String email) {
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
}
