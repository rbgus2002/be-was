package view;

import annotation.RequestMapping;
import annotation.View;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static util.PathList.TEMPLATE_PATH;
@View(path = "/user/profile.html")
public class ProfileView {

    private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);

    public static String changeToDynamic(User user) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(TEMPLATE_PATH.getPath() + "/user/profile.html"));

        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("<h4 class=\"media-heading\">자바지기</h4>")) {
                sb.append("<h4 class=\"media-heading\">").append(user.getName()).append("</h4>");
            } else if (line.contains("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;javajigi@slipp.net</a>")) {
                sb.append("<a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;").append(user.getEmail()).append("</a>");
            } else sb.append(line);
        }
        return sb.toString();
    }
}
