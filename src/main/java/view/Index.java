package view;

import http.Path;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Index {
    private static Index index;

    private Index() {}

    public static Index getInstance() {
        if (index == null) {
            synchronized (Index.class) {
                index = new Index();
            }
        }
        return index;
    }

    public String getRenderingView(User user) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(Path.TEMPLATES.getPath() + "/index.html"));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("로그인") && user != null) {
                sb.append("                <li><a href=\"user/form.html\" role=\"button\">").append(user.getName()).append("님 </a></li>\n");
                continue;
            }
            if (line.contains("로그아웃") && user == null) {
                continue;
            }
            if (line.contains("회원가입") && user != null) {
                continue;
            }

            sb.append(line);
        }

        return sb.toString();
    }
}
