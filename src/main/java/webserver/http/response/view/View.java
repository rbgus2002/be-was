package webserver.http.response.view;

import Service.UserService;
import model.User;
import webserver.util.Parser;
import webserver.util.ViewResolver;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class View {
    private final byte[] body;

    public View(String url, User user) {
        byte[] byteBody = ViewResolver.getContent(url, Parser.getUrlExtension(url));
        if(url.contains("redirect")) {
            body = null;
            return;
        }
        if(url.contains(".html")) {
            String stringBody = parseBody(user, byteBody);
            body = stringBody.getBytes();
            return;
        }
        body = byteBody;
    }

    private static String parseBody(User user, byte[] byteBody) {
        String stringBody = new String(byteBody);
        StringBuilder stringBuilder = new StringBuilder();
        UserService userService = UserService.of();
        if (user != null) {
            stringBody = stringBody.replaceAll("not_login::", "<!--");
            stringBody = stringBody.replaceAll("::not_login", "-->");
            stringBody = stringBody.replaceAll("login::", "");
            stringBody = stringBody.replaceAll("::login", "");
            Collection<User> users = userService.findAll();
            AtomicInteger i = new AtomicInteger(1);
            users.forEach(u -> {
                stringBuilder.append("<tr>");
                stringBuilder.append("<th scope=\"row\">").append(i).append("</th> ");
                stringBuilder.append("<td>").append(u.getUserId()).append("</td> ");
                stringBuilder.append("<td>").append(u.getName()).append("</td> ");
                stringBuilder.append("<td>").append(u.getEmail()).append("</td> ");
                stringBuilder.append("<td> <a href=\"#\" class=\"btn btn-success\" role=\"button\"> 수정 </td>");
                stringBuilder.append("\n</tr>");
                i.getAndIncrement();
            });

            stringBody = stringBody.replaceAll(":model:", stringBuilder.toString());
            return stringBody;
        }
        stringBody = stringBody.replaceAll("not_login::", "");
        stringBody = stringBody.replaceAll("::not_login", "");
        stringBody = stringBody.replaceAll("login::", "<!--");
        stringBody = stringBody.replaceAll("::login", "-->");
        return stringBody;
    }

    public int getLength() {
        return body == null ?0:body.length;
    }

    public byte[] readBody() {
        return body;
    }
}
