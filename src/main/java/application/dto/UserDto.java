package application.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) && Objects.equals(password, userDto.password) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    public static class Builder {
        private final UserDto userDto;

        public Builder() {
            userDto = new UserDto();
        }

        public Builder withUserId(final String userId) {
            userDto.userId = userId;
            return this;
        }

        public Builder withPassword(final String password) {
            userDto.password = password;
            return this;
        }

        public Builder withName(final String name) {
            userDto.name = name;
            return this;
        }

        public Builder withEmail(final String email) {
            userDto.email = email;
            return this;
        }

        public UserDto build() {
            return userDto;
        }
    }
}

