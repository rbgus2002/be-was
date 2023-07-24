package application.infrastructure;

import application.model.User;
import db.Database;
import java.util.Collection;

public class LocalUserRepository implements UserRepository {
    @Override
    public void save(final User user) {
        Database.addUser(user);
    }

    @Override
    public User findUserById(final String userId) {
        return Database.findUserById(userId);
    }

    @Override
    public Collection<User> findAll() {
        return Database.findAll();
    }
}
