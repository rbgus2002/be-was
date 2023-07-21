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
        signUpController = new SignUpController();
        request= HttpRequestParser.getInstance().getRequest(new ByteArrayInputStream(("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").getBytes()));
    }

    @Test
    @DisplayName("request에 담긴 회원의 정보를 토대로 새로운 회원을 추가해야 한다")
    void signUp(){
        signUpController.execute(request);
        User user = Database.findUserById("javajigi");
        assertEquals("javajigi", user.getUserId());
        assertEquals("password", user.getPassword());
        assertEquals("박재성", user.getName());
        assertEquals("javajigi@slipp.net", user.getEmail());
    }
}