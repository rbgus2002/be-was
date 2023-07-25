package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestHeaders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HandlerMapping Test")
class HandlerMappingTest {

    HandlerMapper handlers = new HandlerMapper();

//    @Test
//    @DisplayName("일치하는 path의 메소드를 반환한다.")
//    void getHandler() throws IOException {
//        // given
//        String headerString = "Host: localhost:8080\r\n" +
//                "Connection: keep-alive\r\n" +
//                "Content-Length: 59\r\n" +
//                "Content-Type: application/x-www-form-urlencoded\r\n" +
//                "Accept: */*\r\n";
//        HttpRequestHeaders headers = new HttpRequestHeaders(headerString);
//
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "test123");
//        params.put("password", "password");
//        params.put("name", "userA");
//        params.put("email", "test@gmail.com");
//
//        HttpRequest request = new HttpRequest();
//
//        // when
//        Method method = handlers.getHandler(request);
//
//        //then
//        assertEquals("createUser", method.getName());
//    }
}
