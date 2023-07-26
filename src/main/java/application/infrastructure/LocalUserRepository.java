package application.infrastructure;

import application.model.User;
import db.Database;
import java.util.Collection;
import java.util.Optional;

public class LocalUserRepository implements UserRepository {
    @Override
    public void save(final User user) {
        Database.addUser(user);
    }

    @Override
    public Optional<User> findUserById(final String userId) {
        return Optional.ofNullable(Database.findUserById(userId));
    }

    @Override
    public Collection<User> findAll() {
        return Database.findAll();
    }
}
