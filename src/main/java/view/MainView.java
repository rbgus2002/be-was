package view;

import model.User;
import util.PathList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainView {

    public static String changeToDynamic(User user) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(PathList.TEMPLATE_PATH.getPath() + "/index.html"));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>")) {
                sb.append("                <li><a href=\"user/form.html\" role=\"button\">" + user.getName() + "님 </a></li>\n");
                continue;
            } else if (line.contains("<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>")) {
                continue;
            }


            sb.append(line);
        }

        return sb.toString();
    }
}
