package controller;

import exception.BadRequestException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import service.BoardService;

import java.util.Map;

import static exception.ExceptionList.INVALID_URI;
import static http.FilePath.INDEX;
import static utils.FileIOUtils.loadFromPath;

public class BoardController extends Controller {

    private final BoardService boardService = new BoardService();

    private BoardController() {
    }

    private static class Holder {
        private static final BoardController INSTANCE = new BoardController();
    }

    public static BoardController getInstance() {
        return BoardController.Holder.INSTANCE;
    }

    @Override
    public HttpResponse.ResponseBuilder doGet(HttpRequest httpRequest) {
        throw new BadRequestException(INVALID_URI);
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/qna/create")) {
            return createQnA(httpRequest.getBody());
        }
        throw new BadRequestException(INVALID_URI);
    }

    private HttpResponse.ResponseBuilder createQnA(Map<String, String> parameters) {
        boardService.createBoard(parameters);
        return loadFromPath(HttpStatus.FOUND, INDEX);
    }
}
