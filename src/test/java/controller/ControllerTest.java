package controller;

import db.Database;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerTest {

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
        Controller controller = Controller.getInstance();

        //when
        HttpResponse httpResponse = controller.index();

        //then
        InputStream fileInputStream = FileUtils.class.getResourceAsStream("/templates/index.html");
        byte[] expectedByte = fileInputStream.readAllBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expectedByte);
    }

    @Test
    @DisplayName("create()를 실행하면 DB에 유저를 저장하고 HttpResponse를 반환한다.")
    void createHttpResponse() throws IOException {
        //given
        Controller controller = Controller.getInstance();
        String userId = "abc";
        String password = "1q2w3e4r";
        String name = "kim";
        String email = "a@a.com";
        Map<String, String> userParameters = new HashMap<>();
        userParameters.put("userId", userId);
        userParameters.put("password", password);
        userParameters.put("name", name);
        userParameters.put("email", email);

        //when
        HttpResponse httpResponse = controller.creatUser(userParameters);

        //then
        verifyHttpResponseBody("/templates/index.html", httpResponse);
        verifyUser(userId, password, name, email);
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