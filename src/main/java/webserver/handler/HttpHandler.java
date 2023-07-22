package webserver.handler;

import webserver.exception.BadRequestException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponseMessage;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.mapper.ApplicationControllerClass.executeMethod;

public class HttpHandler {
    private final HttpRequest httpRequest;
    private HttpResponseMessage responseMessage;
    public final String RESOURCE_PATH = "src/main/resources/templates";

    public HttpHandler(HttpRequest httpRequest, HttpResponseMessage responseMessage) {
        this.httpRequest = httpRequest;
        this.responseMessage = responseMessage;
    }

    public void handling() {
        try {
            // 자원을 요청하는 경우
            if (httpRequest.getExtension() != null) {
                handlingResource();
                return;
            }
            handlingController();
        } catch (BadRequestException badRequestException) {
            responseMessage.setStatusLine(HttpStatus.BAD_REQUEST);
        }
    }

    private void handlingResource() throws BadRequestException {
        try {
            byte[] body = Files.readAllBytes(Paths.get(RESOURCE_PATH + httpRequest.getPath()));
            responseMessage.setStatusLine(HttpStatus.OK);
            responseMessage.setBody(body);
        } catch (IOException e) {
            throw new BadRequestException("요청 파일 경로가 잘못되었습니다.");
        }
    }

    private void handlingController() throws BadRequestException {
        try {
            executeMethod(httpRequest);
            responseMessage.setStatusLine(HttpStatus.CREATED);
            responseMessage.setHeader("Location", httpRequest.getPath());

            // todo 리다이렉트 만들어 주기.
        } catch (ReflectiveOperationException e) {
            throw new BadRequestException("올바르지 않은 수행 요청입니다.");
        }
    }

    public HttpResponseMessage getResponseMessage() {
        return responseMessage;
    }
}
