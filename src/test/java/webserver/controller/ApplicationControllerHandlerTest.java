package webserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.BadRequestException;
import webserver.request.HttpRequestMessage;

import java.io.ByteArrayInputStream;

import static utils.StringUtils.NEW_LINE;
import static webserver.controller.ApplicationControllerHandler.executeMethod;
import static webserver.request.HttpRequestParser.parseRequest;

@DisplayName("사용자 컨트롤러 핸들러 테스트")
class ApplicationControllerHandlerTest {

    @BeforeEach
    void setUp() {
        ApplicationMethod.initialize();
    }

    @Test
    @DisplayName("요청 메시지를 받아 확장자가 없는 경우 해당 메소드에 매핑해 메소드를 실행한다.")
    void name() throws BadRequestException, ReflectiveOperationException {
        String message = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1" + NEW_LINE +
                "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Accept: */*" + NEW_LINE + NEW_LINE;
        HttpRequestMessage httpRequestMessage = parseRequest(new ByteArrayInputStream(message.getBytes()));

        executeMethod(httpRequestMessage);
    }
}
