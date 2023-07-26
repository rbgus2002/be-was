package Service;

import db.Database;
import model.User;
import session.SessionStorage;

import java.util.Collection;
import java.util.UUID;

public class UserService {

    private static final UserService userService = new UserService();

    private UserService() {}

    public static UserService of() {
        return userService;
    }

    public void save(User user) {
        Database.addUser(user);
    }

    public boolean canLogin(String userId, String userPw) {
        User user = Database.findUserById(userId);
        if(user == null) {
            return false;
        }
        return user.getPassword().equals(userPw);
    }

    private boolean isLogin(String sessionId) {
        String userId = SessionStorage.getUserId(UUID.fromString(sessionId));
        return userId != null;
    }

    public User getUser(String sId) {
        if(!isLogin(sId)) {
            return null;
        }
        String userId = SessionStorage.getUserId(UUID.fromString(sId));
        return Database.findUserById(userId);
    }

    public Collection<User> findAll() {
        return Database.findAll();
    }
}
