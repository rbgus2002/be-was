package db;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("unchecked")
class DatabaseTest {
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field usersField = Database.class.getDeclaredField("users");
        usersField.setAccessible(true);
        Map<String, User> users = (Map<String, User>) usersField.get(null);
        users.clear();
    }

    @Nested
    @DisplayName("addUser method")
    class AddUser {
        @Test
        @DisplayName("Database 클래스의 Map에 유저를 추가한다")
        void addUserToMapInDatabase() throws ReflectiveOperationException {
            //given
            User user = new User("userId", "password", "name", "email");
            Field usersField = Database.class.getDeclaredField("users");
            usersField.setAccessible(true);
            Map<String, User> users = (Map<String, User>) usersField.get(null);

            //when
            Database.addUser(user);

            //then
            assertThat(users.values().size()).isEqualTo(1);
            assertThat(users.get("userId")).isEqualTo(user);
        }
    }

    @Nested
    @DisplayName("findUserById method")
    class FindUserById {
        @Nested
        @DisplayName("userId에 해당하는 유저가 존재하는 경우")
        class IsUserMatchUserIdExist {
            @Test
            @DisplayName("해당 유저를 반환한다")
            void returnUser() {
                //given
                User user = new User("userId", "password", "name", "email");
                Database.addUser(user);

                //when
                User resultUser = Database.findUserById("userId");

                //then
                assertThat(resultUser).isEqualTo(user);
            }
        }

        @Nested
        @DisplayName("userId에 해당하는 유저가 존재하지 않는 경우")
        class IsUserMatchUserIdNotExist {
            @Test
            @DisplayName("비어있는 Optional 객체를 반환한다")
            void returnEmptyOptional() {
                //given
                //when
                User notExist = Database.findUserById("notExist");

                //then
                assertThat(notExist).isNull();
            }
        }
    }

    @Nested
    @DisplayName("findAll method")
    class FindAll {
        @Test
        @DisplayName("데이터베이스에 존재하는 모든 유저를 반환한다")
        void returnAllUserInDatabase() {
            //given
            User user1 = new User("userId1", "password", "name1", "email");
            User user2 = new User("userId2", "password", "name2", "email");
            Database.addUser(user1);
            Database.addUser(user2);

            //when
            Collection<User> users = Database.findAll();

            //then
            assertThat(users).contains(user1, user2);
        }
    }
}
