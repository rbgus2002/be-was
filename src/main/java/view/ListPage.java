package view;

import exception.SessionIdException;
import http.HttpRequest;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static db.Database.findAll;
import static db.SessionStorage.findAllSessionIds;
import static exception.ExceptionList.NOT_EXIST_SESSION_ID;
import static exception.ExceptionList.NO_SESSION_ID;
import static http.FilePath.LIST;
import static utils.FileIOUtils.TEMPLATES_DIRECTORY;

public class ListPage {
    public String getListPage(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        if (sessionId.equals(""))
            throw new SessionIdException(NO_SESSION_ID);
        if (findAllSessionIds().stream().noneMatch(id -> id.equals(sessionId)))
            throw new SessionIdException(NOT_EXIST_SESSION_ID);

        StringBuilder userList = new StringBuilder("<tbody>\n");
        int i = 1;
        for (User user : findAll()) {
            userList.append("<tr>\n")
                    .append("<th scope=\"row\">").append(i++)
                    .append("</th> <td>").append(user.getUserId())
                    .append("</td> <td>").append(user.getName())
                    .append("</td> <td>").append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                            "</tr>\n");
        }
        userList.append("</tbody>\n");

        StringBuilder listBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + LIST);
        String line;
        boolean tbodyFlag = true;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<tbody>")) {
                    tbodyFlag = false;
                }
                if (tbodyFlag) {
                    listBuilder.append(line);
                }
                if (line.contains("</tbody>")) {
                    tbodyFlag = true;
                    listBuilder.append(userList);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listBuilder.toString();
    }
}
