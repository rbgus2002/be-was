package controller;

import exception.BadRequestException;
import http.HttpRequest;
import http.HttpResponse;

import static exception.ExceptionList.INVALID_URI;

public class BasicController extends Controller {

    @Override
    public HttpResponse.ResponseBuilder doGet(String uri) {
        throw new BadRequestException(INVALID_URI);
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        throw new BadRequestException(INVALID_URI);
    }
}
