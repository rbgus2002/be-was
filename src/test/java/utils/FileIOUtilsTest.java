package utils;

import http.HttpResponse;
import http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileIOUtils.*;

class FileIOUtilsTest {

    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 static 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadStaticFromPathTest() {
        // Given
        HttpResponse.ResponseBuilder responseBuilder = loadFromPath(HttpStatus.OK, "/css/styles.css");

        // When
        HttpResponse httpResponse = responseBuilder.build();

        // Then
        softAssertions.assertThat(httpResponse.getHttpStatus())
                .as("response의 status가 OK가 아닙니다.\n현재 값: %s", httpResponse.getHttpStatus())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 templates 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadTemplatesFromPathTest() {
        HttpResponse.ResponseBuilder responseBuilder = loadFromPath(HttpStatus.OK, "/index.html");

        // When
        HttpResponse httpResponse = responseBuilder.build();

        // Then
        softAssertions.assertThat(httpResponse.getHttpStatus())
                .as("response의 status가 OK가 아닙니다.\n현재 값: %s", httpResponse.getHttpStatus())
                .isEqualTo(HttpStatus.OK);
    }

}