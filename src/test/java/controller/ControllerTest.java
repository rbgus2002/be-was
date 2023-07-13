package controller;

import db.Database;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.*;
import util.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerTest {

    Controller controller = Controller.getInstance();

    @AfterEach
    void tearDown() {
        Database.clear();
    }

    @Test
    @DisplayName("싱글톤으로 생성된다.")
    void createSingleton() {
        //given, when
        Controller c1 = Controller.getInstance();
        Controller c2 = Controller.getInstance();

        //then
        assertThat(c1).isEqualTo(c2);
    }

    @Test
    @DisplayName("index()를 실행하면 HttpResponse를 반환한다.")
    void indexHttpResponse() throws IOException {
        //given
        //when
        HttpResponse httpResponse = controller.index();

        //then
        InputStream fileInputStream = FileUtils.class.getResourceAsStream("/templates/index.html");
        byte[] expectedByte = fileInputStream.readAllBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expectedByte);
    }

    @Test
    @DisplayName("userForm()를 실행하면 HttpResponse를 반환한다.")
    void userFormHttpResponse() throws IOException {
        //given
        //when
        HttpResponse httpResponse = controller.userForm();

        //then
        InputStream fileInputStream = FileUtils.class.getResourceAsStream("/templates/user/form.html");
        byte[] expectedByte = fileInputStream.readAllBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expectedByte);
    }

    @Nested
    class CreateMethod {
        final String userId = "abc";
        final String password = "1q2w3e4r";
        final String name = "kim";
        final String email = "a@a.com";
        final Map<String, String> userParameters = new HashMap<>();

        @BeforeEach
        void setUp() {
            userParameters.put("userId", userId);
            userParameters.put("password", password);
            userParameters.put("name", name);
            userParameters.put("email", email);
        }

        @Test
        @DisplayName("create()를 실행하면 DB에 유저를 저장하고 HttpResponse를 반환한다.")
        void createHttpResponse() throws IOException {
            //given
            //when
            HttpResponse httpResponse = controller.creatUser(userParameters);

            //then
            verifyHttpResponseBody("/templates/index.html", httpResponse);
            verifyUser(userId, password, name, email);
        }

        @Test
        @DisplayName("이미 존재하는 userId면 error.html의 HttpResponse를 반환한다.")
        void createDuplicatedUserId() throws IOException {
            //given
            String existPassword = "123456";
            String existName = "han";
            String existEmail = "b@b.com";
            Database.addUser(new User(userId, existPassword, existName, existEmail));

            //when
            HttpResponse httpResponse = controller.creatUser(userParameters);

            //then
            verifyHttpResponseBody("/templates/error.html", httpResponse);
            verifyUser(userId, existPassword, existName, existEmail);
        }
    }

    private void verifyHttpResponseBody(String path, HttpResponse httpResponse) throws IOException {
        InputStream fileInputStream = FileUtils.class.getResourceAsStream(path);
        byte[] expectedByte = fileInputStream.readAllBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expectedByte);
    }

    private void verifyUser(String userId, String password, String name, String email) {
        User expectedUser = new User(userId, password, name, email);
        User findUser = Database.findUserById(userId);
        assertThat(findUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }
}