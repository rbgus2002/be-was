package controller;

import http.HttpRequest;
import http.HttpResponse;
import service.QnAService;

public class QnAController extends Controller {

    private final QnAService qnaService = new QnAService();

    private QnAController() {
    }

    private static class Holder {
        private static final QnAController INSTANCE = new QnAController();
    }

    public static QnAController getInstance() {
        return QnAController.Holder.INSTANCE;
    }

    @Override
    public HttpResponse.ResponseBuilder doGet(String uri) {
        return null;
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        return null;
    }
}
