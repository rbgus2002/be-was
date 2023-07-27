package view;

import db.ContentDatabase;
import model.Content;
import model.User;
import util.PathList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

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

    public static String indexPage() throws IOException {
        Collection<Content> contents = ContentDatabase.findAll();
        StringBuilder addString = new StringBuilder();
        for (Content content : contents) {
            addString.append("              <li>\n");
            addString.append("                  <div class=\"wrap\">\n");
            addString.append("                      <div class=\"main\">\n");
            addString.append("                          <strong class=\"subject\">\n");
            addString.append("                              <a href=\"./qna/show.html\">" + content.getTitle() + "</a>\n");
            addString.append("                          </strong>\n");
            addString.append("                          <div class=\"auth-info\">\n");
            addString.append("                              <i class=\"icon-add-comment\"></i>\n");
            addString.append("                              <span class=\"time\">" + content.getLocalDate() + "</span>\n");
            addString.append("                              <a href=\"./user/profile.html\" class=\"author\">" + content.getWriter() + "</a>\n");
            addString.append("                          </div>\n");
            addString.append("                          <div class=\"reply\" title=\"댓글\">\n");
            addString.append("                              <i class=\"icon-reply\"></i>\n");
            addString.append("                              <span class=\"point\">12</span>\n");
            addString.append("                          </div>\n");
            addString.append("                      </div>\n");
            addString.append("                  </div>\n");
            addString.append("              </li>");
        }

        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(PathList.TEMPLATE_PATH.getPath() + "/index.html"));
        String line;
        boolean flag = true;
        while ((line = br.readLine()) != null) {
            if (line.contains("<li><a href=\"#\" role=\"button\">로그아웃</a></li>")) {
                continue;
            } else if (line.contains("<li><a href=\"#\" role=\"button\">개인정보수정</a></li>")) {
                continue;
            }

            if (line.contains("<ul class=\"list\">")) {
                sb.append(line);
                flag = false;
            }
            if (flag) {
                sb.append(line);
            }
            if (line.contains("</ul>")) {
                flag = true;
                sb.append(addString);
                sb.append(line);
                addString.setLength(0);
            }
        }

        return sb.toString();

    }
}
