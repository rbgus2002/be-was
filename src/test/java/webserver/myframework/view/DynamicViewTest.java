package webserver.myframework.view;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.myframework.model.Model;
import webserver.myframework.model.ModelImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DynamicView 테스트")
public class DynamicViewTest {
    static String expectContent = "Hello,\n" +
                                  " My Name Is syua\n" +
                                  "Nice to Meet you\n" +
                                  "\n" +
                                  "Users\n" +
                                  " User Name: syua\n" +
                                  " User Name: aaaa\n" +
                                  " User Name: bbbb\n" +
                                  " User Name: cccc\n" +
                                  " User Name: dddd\n";
    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field usersField = Database.class.getDeclaredField("users");
        usersField.setAccessible(true);
        ((Map) usersField.get(null)).clear();
        Database.addUser(new User("aaaa", "aaaa", "aaaa", "aaaa"));
        Database.addUser(new User("bbbb", "bbbb", "bbbb", "bbbb"));
        Database.addUser(new User("cccc", "cccc", "cccc", "cccc"));
        Database.addUser(new User("dddd", "dddd", "dddd", "dddd"));
    }

    @Nested
    @DisplayName("render method")
    class Render {
       @Test
       @DisplayName("동적으로 결과를 생성한다")
       void generateResultDynamically() throws IOException {
           //given
           User user = new User("syua", "syua", "syua", "syua");
           Database.addUser(user);
           Model model = new ModelImpl();
           model.addParameter("user", user);
           model.addParameter("users", new ArrayList<>(Database.findAll()));
           DynamicView dynamicView = new DynamicView(new File("src/test/resources/DynamicViewTestFile"), model);

           //when
           byte[] byteContent = dynamicView.render();
           String content = new String(byteContent, StandardCharsets.UTF_8);

           //then
           assertThat(content).isEqualTo(expectContent);
       }
    }
}
