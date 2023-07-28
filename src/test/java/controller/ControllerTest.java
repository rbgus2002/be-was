package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaders;
import webserver.http.request.HttpRequestStartLine;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller Test")
class ControllerTest {

    Controller controller = new Controller();

    @Test
    @DisplayName("home path 경로가 들어올 때 index.html로 리다이렉트 한다.")
    void home() throws IOException {
        HttpResponse response = controller.home();

        assertEquals(HttpStatus.FOUND, response.getStatus());
    }

    @Test
    @DisplayName("POST 회원가입을 요청하면 입력 정보를 바탕으로 유저가 생성되어야 한다.")
    void createUser() throws IOException {
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HttpRequestStartLine startLine = new HttpRequestStartLine("POST /user/create HTTP/1.1");
        HttpRequestHeaders headers =new HttpRequestHeaders("Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n");
        HttpRequestBody body = new HttpRequestBody("userId=userA&password=password&name=user&email=test@gmail.com");

        HttpRequest request = new HttpRequest(br, startLine, headers, body);

        // when
        HttpResponse httpResponse = controller.createUser(request);

        //then
        assertEquals(HttpStatus.FOUND, httpResponse.getStatus());
    }

    @Test
    @DisplayName("로그인을 시도하면 아이디와 패스워드를 확인하고 세션을 생성한다.")
    void signIn() throws IOException {
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HttpRequestStartLine startLine = new HttpRequestStartLine("POST /user/create HTTP/1.1");
        HttpRequestHeaders headers =new HttpRequestHeaders("Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n");
        HttpRequestBody body = new HttpRequestBody("userId=userA&password=password&name=user&email=test@gmail.com");

        HttpRequest request = new HttpRequest(br, startLine, headers, body);
        HttpResponse httpResponse = controller.createUser(request);

        br = new BufferedReader(new InputStreamReader(System.in));
        startLine = new HttpRequestStartLine("POST /user/login HTTP/1.1");
        headers =new HttpRequestHeaders("Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 6902\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n");
        body = new HttpRequestBody("userId=userA&password=password");
        request = new HttpRequest(br, startLine, headers, body);

        // when
        HttpResponse response = controller.signInUser(request);

        //then
        assertEquals(HttpStatus.FOUND, httpResponse.getStatus());
    }

}
