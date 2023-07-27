package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.fixture.HttpRequestFixture;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrontHandlerTest {
    static final FrontHandler FRONT_HANDLER = new FrontHandler();


    @DisplayName("처리할 수 없는 요청 Not Found 응답")
    @Test
    void notFoundTest() {
        HttpRequest strangeRequest = HttpRequestFixture.getStrangeRequest();

        HttpResponse response = FRONT_HANDLER.handle(strangeRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
    }
}
