package view;

import db.Database;
import http.Path;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class List {

    private static List list;

    private List() {}

    public static List getInstance() {
        if (list == null) {
            synchronized (Index.class) {
                list = new List();
            }
        }
        return list;
    }

    public String getRenderingView(User currentUser) throws IOException {
        Collection<User> users = Database.findAll();
        StringBuilder sb = new StringBuilder();
        StringBuilder userBuilder = new StringBuilder();
        int userCount = users.size();
        for (User user : users) {
            userBuilder.append("<tr>\n");
            userBuilder.append("    <th scope=\"row\">" + (userCount--) + "</th> <td>" + user.getUserId() + "</td> <td>" + user.getName() + "</td> <td>" + user.getEmail() + "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            userBuilder.append("</tr>\n");
        }

        BufferedReader br = new BufferedReader(new FileReader(Path.TEMPLATES.getPath() + "/user/list.html"));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>") && currentUser != null) {
                sb.append("                <li><a href=\"user/form.html\" role=\"button\">").append(currentUser.getName()).append("님 </a></li>\n");
                continue;
            }
            if (line.contains("<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>") && currentUser != null) {
                continue;
            }

            if (line.contains("<tbody>")) {
                sb.append(line);
                sb.append(userBuilder);
                continue;
            }

            sb.append(line);
        }

        return sb.toString();
    }
}
