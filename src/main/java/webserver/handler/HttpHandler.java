package webserver.handler;

import webserver.exception.BadRequestException;
import webserver.request.HttpRequestMessage;
import webserver.response.HttpMIME;
import webserver.response.HttpResponseMessage;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.WebServer.logger;
import static webserver.controller.ApplicationControllerHandler.executeMethod;

public class HttpHandler {
    private final HttpRequestMessage httpRequestMessage;
    private HttpResponseMessage responseMessage;

    public HttpHandler(HttpRequestMessage httpRequestMessage, HttpResponseMessage responseMessage) {
        this.httpRequestMessage = httpRequestMessage;
        this.responseMessage = responseMessage;
    }

    public void handling() {
        try {
            // 자원을 요청하는 경우
            if (httpRequestMessage.getExtension() != null) {
                handlingResource();
                return;
            }
            handlingController();
        } catch (BadRequestException badRequestException) {
            logger.error("찾을 수 없는 리소스입니다. {}", badRequestException.getLocalizedMessage());
            responseMessage.setStatusLine(HttpStatus.BAD_REQUEST);
        }
    }

    private void handlingResource() throws BadRequestException {
        try {
            logger.debug(httpRequestMessage.getPath());
            byte[] body = Files.readAllBytes(Paths.get(httpRequestMessage.getPath()));
            responseMessage.setStatusLine(HttpStatus.OK);
            responseMessage.setBody(body);
            responseMessage.setHeader("Content-Type", HttpMIME.findBy(httpRequestMessage.getExtension()).getType() + "; charset=UTF-8");
        } catch (IOException | IllegalArgumentException e) {
            throw new BadRequestException("요청 파일 경로에 파일이 존재하지 않습니다.");
        }
    }

    private void handlingController() throws BadRequestException {
        try {
            executeMethod(httpRequestMessage);
            responseMessage.setStatusLine(HttpStatus.CREATED);
            responseMessage.setHeader("Location", httpRequestMessage.getPath());

            // todo 리다이렉트 만들어 주기.
        } catch (ReflectiveOperationException e) {
            throw new BadRequestException("올바르지 않은 수행 요청입니다.");
        }
    }

    public HttpResponseMessage getResponseMessage() {
        return responseMessage;
    }
}
