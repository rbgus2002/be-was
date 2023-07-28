package was.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.db.Database;
import was.model.Board;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;

class BoardControllerTest {

	private BoardController boardController = new BoardController();

	@BeforeEach
	void beforeEach() {
		Database.deleteUserAll();
		Database.deleteBoardAll();
	}

	@Test
	@DisplayName("제대로 board가 저장되어야 한다")
	void saveBoard() throws IOException {
		// given
		String input = "POST /board HTTP/1.1\r\n"
			+ "Content-Type: application/x-www-form-urlencoded\r\n"
			+ "Content-Length: 53\r\n"
			+ "\r\n"
			+ "writer=chan&title=test&contents=HelloWorld";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final HttpWasResponse httpWasResponse = new HttpWasResponse(new ByteArrayOutputStream());

		//when
		boardController.writeBoard(httpWasRequest, httpWasResponse);

		//then
		final Board board = Database.findBoardByIndex(1);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(board.getWriter()).as("작성자 테스트").isEqualTo("chan");
		softAssertions.assertThat(board.getTitle()).as("타이틀 테스트").isEqualTo("test");
		softAssertions.assertThat(board.getContents()).as("컨텐츠 테스트").isEqualTo("HelloWorld");
	}
}