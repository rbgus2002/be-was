package view;

import exception.SessionIdException;
import http.HttpRequest;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static db.Database.findUserById;
import static db.SessionStorage.findAllSessionIds;
import static db.SessionStorage.findUserIdBySessionId;
import static exception.ExceptionList.NOT_EXIST_SESSION_ID;
import static exception.ExceptionList.NO_SESSION_ID;
import static http.FilePath.PROFILE;
import static utils.FileIOUtils.TEMPLATES_DIRECTORY;

public class ProfilePage {
    public String getProfilePage(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        if (sessionId.equals(""))
            throw new SessionIdException(NO_SESSION_ID);
        if (findAllSessionIds().stream().noneMatch(id -> id.equals(sessionId)))
            throw new SessionIdException(NOT_EXIST_SESSION_ID);
        String userId = findUserIdBySessionId(sessionId);
        User user = findUserById(userId);

        StringBuilder profileBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + PROFILE);
        String line;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<h4 class=\"media-heading\">자바지기</h4>")) {
                    profileBuilder.append("<h4 class=\"media-heading\">").append(user.getName()).append("</h4>");
                }
                else if (line.contains("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;javajigi@slipp.net</a>")) {
                    profileBuilder.append("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;").append(user.getEmail()).append("</a>");
                }
                else profileBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profileBuilder.toString();
    }
}
