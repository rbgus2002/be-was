package webserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.BadRequestException;
import webserver.request.HttpRequestMessage;
import webserver.response.HttpResponseMessage;

import java.io.ByteArrayInputStream;

import static utils.StringUtils.NEW_LINE;
import static webserver.request.HttpRequestParser.parseRequest;

@DisplayName("사용자 컨트롤러 핸들러 테스트")
class ApplicationControllerHandlerTest {

    @BeforeEach
    void setUp() {
        ApplicationMethod.initialize();
    }

    @Test
    @DisplayName("POST 요청 메시지를 수행한다.")
    void name() throws BadRequestException, ReflectiveOperationException {
        String message = "POST /user/create HTTP/1.1" + NEW_LINE +
                "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 93" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE + NEW_LINE +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpRequestMessage httpRequestMessage = parseRequest(new ByteArrayInputStream(message.getBytes()));

        ApplicationControllerHandler applicationControllerHandler = ApplicationControllerHandler.of(httpRequestMessage, new HttpResponseMessage());
        applicationControllerHandler.executeMethod();
    }
}
