package view;

import http.HttpRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static exception.ExceptionList.NOT_EXIST_SESSION_ID;
import static exception.ExceptionList.NO_SESSION_ID;
import static http.HttpMethod.POST;
import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    private SoftAssertions softAssertions;
    private Page page;

    @BeforeEach
    void setup() {
        softAssertions = new SoftAssertions();
        page = new Page();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("profile.html 요청 시 세션 아이디가 없을 때, NO_SESSION_ID 예외를 던져야 한다")
    void noSessionIdProfile() {
        // Given
        String uri = "/user/profile.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            page.getDynamicPage(httpRequest, uri);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NO_SESSION_ID);
    }

    @Test
    @DisplayName("profile.html 요청 시 세션 아이디가 유효하지 않을 때, NOT_EXIST_SESSION_ID 예외를 던져야 한다")
    void notExistSessionIDProfile() {
        // Given
        String uri = "/user/profile.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=4bc504ae-a64f-4fba-a3df-4466c012915a");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            page.getDynamicPage(httpRequest, uri);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NOT_EXIST_SESSION_ID);
    }

    @Test
    @DisplayName("list.html 요청 시 세션 아이디가 없을 때, NO_SESSION_ID 예외를 던져야 한다")
    void noSessionIdList() {
        // Given
        String uri = "/user/list.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            page.getDynamicPage(httpRequest, uri);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NO_SESSION_ID);
    }

    @Test
    @DisplayName("list.html 요청 시 세션 아이디가 유효하지 않을 때, NOT_EXIST_SESSION_ID 예외를 던져야 한다")
    void notExistSessionIDList() {
        // Given
        String uri = "/user/list.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=4bc504ae-a64f-4fba-a3df-4466c012915a");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            page.getDynamicPage(httpRequest, uri);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NOT_EXIST_SESSION_ID);
    }
}