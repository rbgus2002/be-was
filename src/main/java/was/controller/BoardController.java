package was.controller;

import was.controller.annotation.RequestMapping;
import was.db.Database;
import was.model.Board;
import was.webserver.annotation.Controller;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpStatus;

@Controller
public class BoardController {

	@RequestMapping(method = HttpMethod.POST, path = "/board")
	public void writeBoard(HttpWasRequest request, HttpWasResponse response) {
		final String writer = request.getParameter("writer");
		final String title = request.getParameter("title");
		final String contents = request.getParameter("contents");
		final Board board = new Board(writer, title, contents);

		Database.addBoard(board);

		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader(HttpHeader.LOCATION, "http://localhost:8080/index.html");
	}

}
