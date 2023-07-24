package application.infrastructure;

import application.model.User;
import java.util.Collection;

public interface UserRepository {
    void save(User user);

    User findUserById(String userId);

    Collection<User> findAll();
}
