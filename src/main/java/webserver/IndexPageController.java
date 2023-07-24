package webserver;

import db.Database;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.SessionManager;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class IndexPageController implements Controller {

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String html = createIndexHtml(httpRequest);
        processResponse(httpResponse, html);
    }

    private void processResponse(HttpResponse httpResponse, String html) {
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private String createIndexHtml(HttpRequest httpRequest) throws IOException {
        BufferedReader templateFileReader = getIndexFileReader();

        if (isLoginStatus(httpRequest)) {
            String username = getUsername(httpRequest);
            return createUserIndexPageHtml(templateFileReader, username);
        }
        return createGuestIndexPageHtml(templateFileReader);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie().get("sid");
        return SessionManager.verifySessionId(sessionId);
    }

    private String getUsername(HttpRequest httpRequest) {
        String userId = SessionManager.verifySessionIdAndGetUserId(httpRequest.getCookie().get("sid"));
        return Database.findUserById(userId).getName();
    }

    private String createUserIndexPageHtml(BufferedReader indexFileReader, String username) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (indexFileReader.ready()) {
            String line = indexFileReader.readLine();

            if (line.contains("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>")) {
                stringBuilder.append("<li><a href=\"#\" role=\"button\">" + username + "</a></li>\r\n");
                continue;
            }

            stringBuilder.append(line).append("\r\n");
        }

        return stringBuilder.toString();
    }

    private String createGuestIndexPageHtml(BufferedReader indexFileReader) {
        return indexFileReader.lines().collect(Collectors.joining("\r\n"));
    }

    private BufferedReader getIndexFileReader() throws IOException {
        byte[] htmlBytes = FileUtils.readFileBytes("/index.html");
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(htmlBytes)));
    }
}
