package controller;

import db.Database;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    String inputValue;
    InputStream inputStream;
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    UserController userController;

    @BeforeEach
    void setUp() throws IOException {
        inputValue =
                "GET /user/create?userId=asdf&password=asdf&name=asdf&email=asdf@naver.com HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\n" +
                        "sec-ch-ua-mobile: ?0\n" +
                        "sec-ch-ua-platform: \"macOS\"\n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                        "Sec-Fetch-Site: none\n" +
                        "Sec-Fetch-Mode: navigate\n" +
                        "Sec-Fetch-User: ?1\n" +
                        "Sec-Fetch-Dest: document\n" +
                        "Accept-Encoding: gzip, deflate, br\n" +
                        "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6,zh;q=0.5\n" +
                        "\n";

        inputStream = new ByteArrayInputStream(inputValue.getBytes());

        httpRequest = new HttpRequest(inputStream);
        httpResponse = new HttpResponse(httpRequest);
        userController = new UserController();
    }

    @Test
    @DisplayName("controller에서 user를 생성하고, 잘 저장되는지 확인한다.")
    void getUserCreate() {
        User expectedUser = new User("asdf", "asdf", "asdf", "asdf@naver.com");

        String result = userController.getUserCreate(httpRequest, httpResponse);
        Collection<User> userSet = Database.findAll();

        assertEquals(1, userSet.stream().count());
        userSet.stream().forEach(user -> assertEquals(expectedUser, user));
        assertEquals("redirect:/", result);
    }
}