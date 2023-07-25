package view;

import http.HttpRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static exception.ExceptionList.*;
import static http.HttpMethod.POST;
import static org.junit.jupiter.api.Assertions.*;

class ProfilePageTest {

    private SoftAssertions softAssertions;
    private ProfilePage profilePage;

    @BeforeEach
    void setup() {
        softAssertions = new SoftAssertions();
        profilePage = new ProfilePage();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("세션 아이디가 없을 시, NO_SESSION_ID 예외를 던져야 한다")
    void noSessionId() {
        // Given
        String uri = "/user/profile.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            profilePage.getProfilePage(httpRequest);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NO_SESSION_ID);
    }

    @Test
    @DisplayName("세션 아이디가 유효하지 않을 경우, NOT_EXIST_SESSION_ID 예외를 던져야 한다")
    void notExistSessionID() {
        // Given
        String uri = "/user/profile.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=4bc504ae-a64f-4fba-a3df-4466c012915a");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            profilePage.getProfilePage(httpRequest);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NOT_EXIST_SESSION_ID);
    }


}