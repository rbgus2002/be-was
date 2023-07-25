package webserver.handler;

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
    private HttpResponseMessage httpResponseMessage;

    public HttpHandler(HttpRequestMessage httpRequestMessage, HttpResponseMessage httpResponseMessage) {
        this.httpRequestMessage = httpRequestMessage;
        this.httpResponseMessage = httpResponseMessage;
    }

    public void handling() {
        if (httpRequestMessage.getExtension() != null) {
            // 자원을 요청하는 경우
            handlingResource();
            return;
        }
        handlingController();
    }

    private void handlingResource() {
        try {
            logger.debug(httpRequestMessage.getPath());
            byte[] body = Files.readAllBytes(Paths.get(httpRequestMessage.getPath()));
            httpResponseMessage.setStatusLine(HttpStatus.OK);
            httpResponseMessage.setBody(body);
            httpResponseMessage.setHeader("Content-Type", HttpMIME.findBy(httpRequestMessage.getExtension()).getType() + "; charset=UTF-8");
        } catch (IOException | IllegalArgumentException e) {
            httpResponseMessage.setStatusLine(HttpStatus.NOT_FOUND);
            httpResponseMessage.setBody("");
            logger.error("요청 파일 경로에 파일이 존재하지 않습니다. {}", e.getLocalizedMessage());
        }
    }

    private void handlingController() {
        try {
            // 결과값 반환
            Object returnValue = executeMethod(httpRequestMessage);
            httpResponseMessage.setStatusLine(HttpStatus.CREATED);

            if (returnValue == null) {
                return;
            }
            // String을 받은 상황
            if (returnValue.getClass().equals(String.class)) {
                String resultStringValue = String.valueOf(returnValue);

                // 리다이렉트를 처리해야 하는 상황
                if (resultStringValue.startsWith("redirect:")) {
                    String redirectPath = resultStringValue.substring(resultStringValue.indexOf(":") + 1);
                    logger.debug(redirectPath);
                    httpResponseMessage.setStatusLine(HttpStatus.FOUND);
                    httpResponseMessage.setHeader("Location", redirectPath);
                    logger.debug(httpResponseMessage.toString());
                    return;
                }
            }

        } catch (ReflectiveOperationException e) {
            httpResponseMessage.setStatusLine(HttpStatus.BAD_REQUEST);
            httpResponseMessage.setBody("");
            logger.error("올바르지 않은 수행 요청입니다.");
        }
    }
}
