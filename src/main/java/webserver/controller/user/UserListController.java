package webserver.controller.user;

import db.Database;
import model.User;
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

public class UserListController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (isLoginStatus(httpRequest)) {
            processResponseWithUserList(httpResponse);
            return;
        }
        processResponseWithRedirectionToLoginPage(httpResponse);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie().get("sid");
        return SessionManager.verifySessionId(sessionId);
    }

    private void processResponseWithUserList(HttpResponse httpResponse) throws IOException {
        BufferedReader templateFileReader = getUserListTemplateFileReader();
        String html = createUserListHtml(templateFileReader);

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private void processResponseWithRedirectionToLoginPage(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/user/login.html");
    }

    private BufferedReader getUserListTemplateFileReader() throws IOException {
        byte[] htmlBytes = FileUtils.readFile("/user/list.html");
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(htmlBytes)));
    }

    private String createUserListHtml(BufferedReader templateFileReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (templateFileReader.ready()) {
            addHtmlLine(templateFileReader, stringBuilder);
        }

        return stringBuilder.toString();
    }

    private void addHtmlLine(BufferedReader templateFileReader, StringBuilder stringBuilder) throws IOException {
        String line = templateFileReader.readLine();

        stringBuilder.append(line).append("\r\n");

        if (line.contains("<tbody>")) {
            appendUserListToHtml(stringBuilder);
        }
    }

    private void appendUserListToHtml(StringBuilder stringBuilder) {
        User[] users = Database.findAll().toArray(new User[0]);

        for (int index = 0; index < users.length; index++) {
            stringBuilder
                    .append("                <tr>\r\n")
                    .append("                    ")
                    .append("<th scope=\"row\">").append(index + 1).append("</th> ")
                    .append("<td>").append((users[index]).getUserId()).append("</td> ")
                    .append("<td>").append((users[index]).getName()).append("</td> ")
                    .append("<td>").append((users[index]).getEmail()).append("</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\r\n")
                    .append("                </tr>\r\n");
        }
    }
}
