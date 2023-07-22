package application.dto;

public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    private UserDto() {
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

    public static class Builder {
        private final UserDto userDto;

        public Builder() {
            userDto = new UserDto();
        }

        public Builder withUserId(String userId) {
            userDto.userId = userId;
            return this;
        }

        public Builder withPassword(String password) {
            userDto.password = password;
            return this;
        }

        public Builder withName(String name) {
            userDto.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            userDto.email = email;
            return this;
        }

        public UserDto build() {
            return userDto;
        }
    }
}

