package db;

import model.Board;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static db.BoardDatabase.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardDatabaseTest {

    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        clearBoards();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("글 정보를 데이터베이스에 추가할 수 있어야 한다")
    void addBoardTest() {
        // Given
        Board board = new Board("kimahhh", "제목", "본문");

        // When
        addBoard(board);

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("글 정보가 추가되지 않았습니다.")
                .isEqualTo(1);

        // Given
        Board board2 = new Board("gildong", "내 이름이 뭘까요", "내 이름은 홍길동");

        // When
        addBoard(board2);

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("글 정보가 추가되지 않았습니다.")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("모든 글 정보를 조회할 수 있어야 한다")
    void findAllBoardsTest() {
        // Given
        Board board1 = new Board("kimahhh", "제목", "본문");
        addBoard(board1);
        Board board2 = new Board("gildong", "내 이름이 뭘까요", "내 이름은 홍길동");
        addBoard(board2);
        Board board3 = new Board("juice", "주스 먹고 싶다", "오렌지주스");
        addBoard(board3);

        // When
        Collection<Board> allBoards = findAllBoards();

        // Then
        softAssertions.assertThat(allBoards.contains(board1))
                .as("board1이 포함되어 있지 않습니다.")
                .isEqualTo(true);
        softAssertions.assertThat(allBoards.contains(board2))
                .as("board2가 포함되어 있지 않습니다.")
                .isEqualTo(true);
        softAssertions.assertThat(allBoards.contains(board3))
                .as("board3이 포함되어 있지 않습니다.")
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Board Id로 Board를 조회할 수 있어야 한다")
    void findBoardByIdTest() {
        // Given
        Board board1 = new Board("kimahhh", "제목", "본문");
        addBoard(board1);
        Board board2 = new Board("gildong", "내 이름이 뭘까요", "내 이름은 홍길동");
        addBoard(board2);
        Board board3 = new Board("juice", "주스 먹고 싶다", "오렌지주스");
        addBoard(board3);

        // When
        Board findBoard_1 = findBoardById((long) 0);
        Board findBoard_2 = findBoardById((long) 1);
        Board findBoard_3 = findBoardById((long) 2);

        // Then
        softAssertions.assertThat(findBoard_1)
                .as("Board Id로 Board를 조회할 수 없습니다.")
                .isEqualTo(board1);
        softAssertions.assertThat(findBoard_2)
                .as("Board Id로 Board를 조회할 수 없습니다.")
                .isEqualTo(board2);
        softAssertions.assertThat(findBoard_3)
                .as("Board Id로 Board를 조회할 수 없습니다.")
                .isEqualTo(board3);
    }

    @Test
    @DisplayName("Board 정보를 초기화할 수 있어야 한다")
    void clearBoardTest() {
        // Given
        Board board1 = new Board("kimahhh", "제목", "본문");
        addBoard(board1);
        Board board2 = new Board("gildong", "내 이름이 뭘까요", "내 이름은 홍길동");
        addBoard(board2);
        Board board3 = new Board("juice", "주스 먹고 싶다", "오렌지주스");
        addBoard(board3);

        // When
        clearBoards();

        // Then
        softAssertions.assertThat(getBoardSize())
                .as("Board Database가 초기화되지 않았습니다.")
                .isEqualTo(0);
    }

}