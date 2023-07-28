package view;

import annotation.View;
import db.Database;
import model.User;
import webserver.HTTPServletRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import static util.PathList.TEMPLATE_PATH;
@View(path = "/user/list.html")
public class ListView implements ViewBase {
    public String changeToDynamic(HTTPServletRequest request) throws IOException {
        Collection<User> all = Database.findAll();
        StringBuilder sb = new StringBuilder();
        StringBuilder addString = new StringBuilder();
        //Bufferedreader new fileReader
        int index = 1;
        for (User user : all) {
            addString.append("                <tr>\n");
            addString.append("                    <th scope=\"row\">" + (index++) + "</th> <td>" + user.getUserId() + "</td> <td>" + user.getName() + "</td> <td>" + user.getEmail() + "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            addString.append("                </tr>\n");
        }

        BufferedReader br = new BufferedReader(new FileReader(TEMPLATE_PATH.getPath() + "/user/list.html"));
        String line = "";
        boolean flag = true;
        while ((line = br.readLine()) != null) {
            if (line.contains("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>")) {
                continue;
            } else if (line.contains("<li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>")) {
                continue;
            }

            if (line.contains("<tbody>")) {
                sb.append(line);
                flag = false;
            }
            if (flag) {
                sb.append(line);
            }
            if (line.contains("</tbody>")) {
                flag = true;
                sb.append(addString);
                sb.append(line);
            }
        }


        return sb.toString();
    }
}
