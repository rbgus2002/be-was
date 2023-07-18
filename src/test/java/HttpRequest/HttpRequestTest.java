package utils;

import db.Database;
import http.HttpRequest;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.StringUtil;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static util.StringUtil.*;

class HttpRequestTest {

    @Test
    @DisplayName("HttpRequest 테스트")
    void test() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(appendNewLine("GET /index.html HTTP/1.1"))
                .append(appendNewLine("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"))
                .append(appendNewLine("Connection: keep-alive"));
        InputStream in = new ByteArrayInputStream(sb.toString().getBytes());

        HttpRequest httpRequest = new HttpRequest(in);

        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();
        String version = httpRequest.getVersion();
        Map<String, String> headers = httpRequest.getHeaders();

        assertThat(method).isEqualTo("GET");
        assertThat(path).isEqualTo("/index.html");
        assertThat(version).isEqualTo("HTTP/1.1");
        assertThat(headers).hasSize(2)
                .contains(entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"))
                .contains(entry("Connection", "keep-alive"));
    }


    @Test
    @DisplayName("HttpRequest GET 회원가입 테스트")
    void signInTest() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(appendNewLine("GET /user/create?userId=javajigi&password=password&name=jaehong&email=kjhonggg@mail.com HTTP/1.1"))
                .append(appendNewLine("Host: localhost:8080"))
                .append(appendNewLine("Connection: keep-alive"))
                .append(appendNewLine("Accept: */*"));
        InputStream in = new ByteArrayInputStream(sb.toString().getBytes());

        HttpRequest httpRequest = new HttpRequest(in);

        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();
        String version = httpRequest.getVersion();
        Map<String, String> params = httpRequest.getParams();
        Map<String, String> headers = httpRequest.getHeaders();

        assertThat(method).isEqualTo("GET");
        assertThat(path).isEqualTo("/user/create");
        assertThat(version).isEqualTo("HTTP/1.1");
        assertThat(params).hasSize(4)
                .contains(entry("userId", "javajigi"))
                .contains(entry("password", "password"))
                .contains(entry("name", "jaehong"))
                .contains(entry("email", "kjhonggg@mail.com"));
        assertThat(headers).hasSize(3)
                .contains(entry("Host", "localhost:8080"))
                .contains(entry("Connection", "keep-alive"))
                .contains(entry("Accept", "*/*"));

        User user = new User(params.get("userId"),params.get("password"),params.get("name"),params.get("email"));
        Database.addUser(user);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("jaehong");
        assertThat(user.getEmail()).isEqualTo("kjhonggg@mail.com");
    }
}