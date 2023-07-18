package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.fixture.HttpRequestFixture;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.FileUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestHandlerTest {
    static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    @DisplayName("/index.html로 들어온 요청 정상 처리")
    @Test
    void indexTest() {
        HttpRequest indexRequest = HttpRequestFixture.getRequestIndex();

        HttpResponse response = httpRequestHandler.handle(indexRequest);

        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertThat(response.getBody())
                .isEqualTo(FileUtils.readFile("src/main/resources/templates/index.html"));
    }

    @DisplayName("처리할 수 없는 요청 Not Found 응답")
    @Test
    void notFoundTest() {
        HttpRequest strangeRequest = HttpRequestFixture.getStrangeRequest();

        HttpResponse response = httpRequestHandler.handle(strangeRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
    }
}
