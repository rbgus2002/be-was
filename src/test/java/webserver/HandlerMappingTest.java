package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HandlerMapping Test")
class HandlerMappingTest {

    HandlerMapper handlerMapping = new HandlerMapper();

    @Test
    @DisplayName("일치하는 path의 메소드를 반환한다.")
    void getHandler() {
        // given
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Accept", "text/css,*/*;q=0.1");
        headers.put("Connection", "keep-alive");

        Map<String, String> params = new HashMap<>();
        params.put("userId", "test123");
        params.put("password", "password");
        params.put("name", "userA");
        params.put("email", "test@gmail.com");

        HttpRequest request = new HttpRequest(HttpMethod.GET, "/user/create", "HTTP/1.1",
                null, headers, params);

        // when
        Method method = handlerMapping.getHandler(request);

        //then
        assertEquals("createUser", method.getName());
    }
}
