package controller;

import exception.BadRequestException;
import http.HttpRequest;
import http.HttpResponse;
import service.BoardService;

import java.util.Map;

import static db.SessionStorage.findUserIdBySessionId;
import static exception.ExceptionList.INVALID_URI;

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
            return createQnA(httpRequest.getBody(), findUserIdBySessionId(httpRequest.getSessionId()));
        }
        throw new BadRequestException(INVALID_URI);
    }

    private HttpResponse.ResponseBuilder createQnA(Map<String, String> parameters, String userId) {
        return null;
    }
}
