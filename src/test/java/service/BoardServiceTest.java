package service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static db.BoardDatabase.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {
    BoardService boardService;
    SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        boardService = new BoardService();
        softAssertions = new SoftAssertions();
        clearBoards();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Board를 생성해서 DB에 저장할 수 있어야 한다")
    void createBoardTest() {
        // Given
        Map<String, String> board1 = new HashMap<>();
        board1.put("writer", "kimahhh");
        board1.put("title", "제목");
        board1.put("contents", "본문");

        // When
        boardService.createBoard(board1);

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("Database의 크기가 1이 아닙니다.")
                .isEqualTo(1);
        softAssertions.assertThat(findBoardById((long) 0).getTitle())
                .as("Id로 Board를 찾을 수 없습니다.")
                .isEqualTo("제목");

        // Given
        Map<String, String> board2 = new HashMap<>();
        board2.put("writer", "gildong");
        board2.put("title", "내 이름이 뭘까요");
        board2.put("contents", "내 이름은 홍길동");

        // When
        boardService.createBoard(board2);

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("Database의 크기가 2가 아닙니다.")
                .isEqualTo(2);
        softAssertions.assertThat(findBoardById((long) 1).getWriter())
                .as("Id로 Board를 찾을 수 없습니다.")
                .isEqualTo("gildong");
    }
}