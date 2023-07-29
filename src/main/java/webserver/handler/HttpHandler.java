package webserver.handler;

import application.model.User;
import db.Database;
import webserver.controller.ApplicationControllerHandler;
import webserver.http.constant.HttpMIME;
import webserver.http.constant.HttpStatus;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;
import webserver.session.SessionStorage;
import webserver.view.ViewData;
import webserver.view.ViewRender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static webserver.WebServer.logger;

public class HttpHandler {
    private final HttpRequestMessage httpRequestMessage;
    private HttpResponseMessage httpResponseMessage;

    public HttpHandler(HttpRequestMessage httpRequestMessage, HttpResponseMessage httpResponseMessage) {
        this.httpRequestMessage = httpRequestMessage;
        this.httpResponseMessage = httpResponseMessage;
    }

    public void handling() {
        if (isHtmlResourceRequest()) {
            handlingHtmlResource();
            return;
        }
        if (isResourceRequest()) {
            handlingResource();
            return;
        }
        handlingController();
    }

    private boolean isResourceRequest() {
        return httpRequestMessage.getExtension() != null;
    }

    private boolean isHtmlResourceRequest() {
        return httpRequestMessage.getExtension() != null && httpRequestMessage.getExtension().equals(".html");
    }

    private void handlingHtmlResource() {
        try {
            logger.debug(httpRequestMessage.getPath());
            httpResponseMessage.setStatusLine(HttpStatus.OK);
            // todo 리팩토링 필요 : 쿠키를 받아 렌더링 하는 로직.
            Map<String, String> matchedData = new HashMap<>();
            if (httpRequestMessage.hasHeader("Cookie")) {
                String[] tokens = httpRequestMessage.getHeader("Cookie").split(";");
                for (String token : tokens) {
                    String[] keyValue = token.trim().split("=");
                    if (keyValue[0].equals("sid") && SessionStorage.hasSession(keyValue[1])) {
                        String userId = SessionStorage.getSession(keyValue[1]);
                        matchedData.put("userId", userId);
                        matchedData.put("href", "/index.html");
                        matchedData.put("username", Database.findUserById(userId).getName());
                        matchedData.put("useremail", Database.findUserById(userId).getEmail());
                    }
                }
            }
            Map<String, List<List<String>>> lineByLineData = new HashMap<>();
            // user db 넣기
            List<List<String>> usersData = new ArrayList<>();
            for (User user : Database.findAll()) {
                List<String> userData = new ArrayList<>();
                userData.add(user.getUserId());
                userData.add(user.getName());
                userData.add(user.getEmail());
                usersData.add(userData);
            }
            lineByLineData.put("user", usersData);

            ViewData viewData = ViewData.of(httpRequestMessage.getPath(), matchedData, lineByLineData);
            String body = ViewRender.createPage(viewData);
            httpResponseMessage.setBody(body);
            httpResponseMessage.setHeader("Content-Type", HttpMIME.findBy(httpRequestMessage.getExtension()).getType() + "; charset=UTF-8");
        } catch (IOException e) {
            httpResponseMessage.setStatusLine(HttpStatus.NOT_FOUND);
            httpResponseMessage.setBody("");
            logger.error("요청 파일 경로에 파일이 존재하지 않습니다. {}", e.getLocalizedMessage());
        }
    }

    private void handlingResource() {
        try {
            logger.debug(httpRequestMessage.getPath());
            byte[] body = Files.readAllBytes(Paths.get(httpRequestMessage.getPath()));
            httpResponseMessage.setStatusLine(HttpStatus.OK);
            httpResponseMessage.setBody(body);
            httpResponseMessage.setHeader("Content-Type", HttpMIME.findBy(httpRequestMessage.getExtension()).getType() + "; charset=UTF-8");
        } catch (IOException e) {
            httpResponseMessage.setStatusLine(HttpStatus.NOT_FOUND);
            httpResponseMessage.setBody("");
            logger.error("요청 파일 경로에 파일이 존재하지 않습니다. {}", e.getLocalizedMessage());
        }
    }

    private void handlingController() {
        ApplicationControllerHandler applicationHandler = ApplicationControllerHandler.of(httpRequestMessage, httpResponseMessage);
        try {
            // 결과값 반환
            Object returnValue = applicationHandler.executeMethod();
            httpResponseMessage.setStatusLine(HttpStatus.CREATED);

            if (!hasReturnValue(returnValue)) {
                httpResponseMessage.setStatusLine(HttpStatus.OK);
                return;
            }
            // String을 받은 상황
            if (isSameClass(returnValue, String.class)) {
                String resultStringValue = String.valueOf(returnValue);

                // 리다이렉트를 처리해야 하는 상황
                if (resultStringValue.startsWith("redirect:")) {
                    String redirectPath = resultStringValue.substring(resultStringValue.indexOf(":") + 1);
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

    private static boolean isSameClass(Object returnValue, Class<?> clazz) {
        return returnValue.getClass().equals(clazz);
    }

    private static boolean hasReturnValue(Object returnValue) {
        return returnValue != null;
    }
}
