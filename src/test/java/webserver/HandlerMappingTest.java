package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

class HandlerMappingTest {
    private final String POST = "POST";
    private final String PATH = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private final Uri URI = Uri.from(PATH);
    private String requestStr;
    private InputStream in;

    @BeforeEach
    void setRequestStr() {
        requestStr = POST + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                "";
        in = new ByteArrayInputStream(requestStr.getBytes());
    }

    @Test
    @DisplayName("GetMapping 어노테이션이 붙은 메소드를 실행한다")
    void doDispatch() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // given
        HttpRequest request = HttpRequest.from(in);

        // when
        Method method = HandlerMapping.getMethodMapped(request);

        // then
        assertNotNull(method);
    }

    @Test
    @DisplayName("Controller의 메소드를 미리 Map에 할당해둔다")
    void confirmStaticMap() throws IOException {
        // given
        HttpRequest request = HttpRequest.from(in);

        // when
        Method method = HandlerMapping.getMethodMapped(request);

        // then
        assertEquals("createUser", method.getName());
    }


}