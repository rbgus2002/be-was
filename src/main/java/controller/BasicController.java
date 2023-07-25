package controller;

import exception.BadRequestException;
import http.HttpRequest;
import http.HttpResponse;

import static exception.ExceptionList.INVALID_URI;

public class BasicController extends Controller {

    private BasicController() {
    }

    private static class Holder {
        private static final BasicController INSTANCE = new BasicController();
    }

    public static BasicController getInstance() {
        return BasicController.Holder.INSTANCE;
    }

    @Override
    public HttpResponse.ResponseBuilder doGet(String uri) {
        throw new BadRequestException(INVALID_URI);
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        throw new BadRequestException(INVALID_URI);
    }
}
