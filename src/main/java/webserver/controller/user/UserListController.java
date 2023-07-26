package webserver.controller.user;

import db.SessionDatabase;
import db.UserDatabase;
import model.User;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.CookieConstants;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;
import webserver.utils.Location;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserListController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (isLoginStatus(httpRequest)) {
            respondWithUserList(httpResponse);
            return;
        }
        httpResponse.sendRedirect(Location.LOGIN_PAGE);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie().get(CookieConstants.SESSION_ID);
        return SessionDatabase.verifySessionId(sessionId);
    }

    private void respondWithUserList(HttpResponse httpResponse) throws IOException {
        String html = createUserListHtml();

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private String createUserListHtml() throws IOException {
        BufferedReader templateHtmlReader = getTemplateHtmlReader();
        StringBuilder stringBuilder = new StringBuilder();

        while (templateHtmlReader.ready()) {
            addHtmlLine(templateHtmlReader.readLine(), stringBuilder);
        }
        return stringBuilder.toString();
    }

    private BufferedReader getTemplateHtmlReader() throws IOException {
        byte[] htmlBytes = FileUtils.readFileBytes("/user/list.html");
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(htmlBytes)));
    }

    private void addHtmlLine(String line, StringBuilder stringBuilder) {
        stringBuilder.append(line).append("\r\n");
        if (line.contains("<tbody>")) {
            appendUserList(stringBuilder);
        }
    }

    private void appendUserList(StringBuilder stringBuilder) {
        User[] users = UserDatabase.findAll().toArray(new User[0]);

        for (int index = 0; index < users.length; index++) {
            stringBuilder.append("                <tr>\r\n")
                    .append("                    ")
                    .append("<th scope=\"row\">").append(index + 1).append("</th> ")
                    .append("<td>").append(users[index].getUserId()).append("</td> ")
                    .append("<td>").append(users[index].getName()).append("</td> ")
                    .append("<td>").append(users[index].getEmail()).append("</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\r\n")
                    .append("                </tr>\r\n");
        }

    }
}
