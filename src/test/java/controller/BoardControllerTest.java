package controller;

import http.HttpRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static db.BoardDatabase.*;
import static http.HttpMethod.POST;
import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    BoardController boardController;
    SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        boardController = BoardController.getInstance();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("글 작성 시, Database에 정보가 추가되어야 한다")
    void createBoardTest() {
        // Given
        clearBoards();
        String uri = "/qna/create";
        String body = "writer=kimahhh&title=제목&contents=본문";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=4bc504ae-a64f-4fba-a3df-4466c012915a");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setBody(body)
                .setHeader(headers)
                .build();

        // When
        boardController.loadFileByRequest(httpRequest);

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("Database의 크기가 1이 아닙니다.")
                .isEqualTo(1);
        softAssertions.assertThat(findBoardById((long) 0).getTitle())
                .as("Id로 Board를 찾을 수 없습니다.")
                .isEqualTo("제목");

    }
}