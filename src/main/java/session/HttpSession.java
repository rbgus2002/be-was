package session;

import dto.UserResponseDto;
import model.User;

import java.util.List;

public class HttpSession {
    public static final long EXPIRE_HOUR = 60 * 60 * 1000;
    private List<Object> contents;
    private long lastAccessTime;

    public HttpSession(List<Object> contents) {
        this.contents = contents;
        lastAccessTime = System.currentTimeMillis();
    }

    public UserResponseDto getUserData() {
        for (var content : contents) {
            if (content.getClass().equals(User.class)) {
                return UserResponseDto.of((User) content);
            }
        }
        return null;
    }

    private boolean isExpired() {
        return lastAccessTime + EXPIRE_HOUR < System.currentTimeMillis();
    }
}
