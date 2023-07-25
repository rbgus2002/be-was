package view;

import http.HttpRequest;

import java.io.*;

import static db.SessionDatabase.findAllSessionIds;
import static http.FilePath.INDEX;
import static utils.FileIOUtils.TEMPLATES_DIRECTORY;

public class IndexPage {
    public String getIndexPage(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();

        StringBuilder indexBuilder = new StringBuilder();
        File indexFile = new File(TEMPLATES_DIRECTORY + INDEX);
        String line;
        try {
            BufferedReader index = new BufferedReader(new FileReader(indexFile));
            while ((line = index.readLine()) != null) {
                if (line.contains("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>") || line.contains("<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>")) {
                    if (isSessionIdValid(sessionId)) continue;
                }
                if (line.contains("<li><a href=\"user/logout.html\" role=\"button\">로그아웃</a></li>") || line.contains("<li><a href=\"#\" role=\"button\">개인정보수정</a></li>")) {
                    if (!isSessionIdValid(sessionId)) continue;
                }
                indexBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return indexBuilder.toString();
    }

    private boolean isSessionIdValid(String sessionId) {
        if (sessionId.equals(""))
            return false;
        return findAllSessionIds().stream().anyMatch(id -> id.equals(sessionId));
    }
}
