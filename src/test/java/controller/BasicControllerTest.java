package controller;

import http.HttpRequest;
import http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.HttpMethod.GET;
import static http.HttpStatus.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BasicControllerTest {

    BasicController basicController;
    SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        basicController = new BasicController();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 리소스에 대한 요청 시 NOT_FOUND 404 상태를 반환해야 한다")
    void wrongRequestNoFile() {
        // Given
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(GET, "/no_index.html", "HTTP/1.1").build();

        // When
        HttpResponse httpResponse = basicController.loadFileByRequest(httpRequest).build();

        // Then
        assertThat(httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
    }

}