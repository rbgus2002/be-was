package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SignUpControllerTest {

    SignUpController signUpController;

    HttpRequest request;

    HttpResponse response;

    @BeforeEach
    void init() throws IOException {
        signUpController = SignUpController.getInstance();
        request= HttpRequestParser.getRequest(new ByteArrayInputStream(("POST /user/create HTTP/1.1\n" +
                "Content-Type: text/plain\n" +
                "User-Agent: PostmanRuntime/7.32.3\n" +
                "Accept: */*\n" +
                "Postman-Token: 9f36b603-ca75-4f6d-8a40-d8774a7876a7\n" +
                "Host: localhost:8080\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net").getBytes()));

        response = new HttpResponse();
    }

    @Test
    @DisplayName("request에 담긴 회원의 정보를 토대로 새로운 회원을 추가해야 한다")
    void signUp(){
        signUpController.execute(request, response);
        User user = Database.findUserById("javajigi");
        assertEquals("javajigi", user.getUserId());
        assertEquals("password", user.getPassword());
        assertEquals("박재성", user.getName());
        assertEquals("javajigi@slipp.net", user.getEmail());
    }
}