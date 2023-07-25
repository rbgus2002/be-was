package Service;

import db.Database;
import model.User;

public class UserService {

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
}
