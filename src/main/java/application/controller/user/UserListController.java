package application.controller.user;

import application.controller.Controller;
import application.dto.user.UserListDto;
import application.service.SessionService;
import application.service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.CookieConstants;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;
import webserver.utils.Location;

import java.io.IOException;
import java.util.List;

public class UserListController implements Controller {
    private final UserService userService = UserService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (isLoginStatus(httpRequest)) {
            respondWithUserList(httpResponse);
            return;
        }
        httpResponse.sendRedirect(Location.LOGIN_PAGE);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(CookieConstants.SESSION_ID);
        return sessionService.verifySessionId(sessionId);
    }

    private void respondWithUserList(HttpResponse httpResponse) throws IOException {
        String html = createUserListHtml();

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private String createUserListHtml() throws IOException {
        String templateHtml = FileUtils.readFileAsString(Location.USER_LIST_PAGE);
        return templateHtml.replace("${userList}", getUserListHtml());
    }

    private String getUserListHtml() {
        StringBuilder stringBuilder = new StringBuilder();

        List<UserListDto> userList = userService.getList();

        for (int index = 0; index < userList.size(); index++) {
            stringBuilder.append("                <tr>\r\n")
                    .append("                    ")
                    .append("<th scope=\"row\">").append(index + 1).append("</th> ")
                    .append("<td>").append(userList.get(index).getUserId()).append("</td> ")
                    .append("<td>").append(userList.get(index).getName()).append("</td> ")
                    .append("<td>").append(userList.get(index).getEmail()).append("</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\r\n")
                    .append("                </tr>\r\n");
        }

        return stringBuilder.toString();
    }
}
