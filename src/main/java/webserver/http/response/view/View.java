package webserver.http.response.view;

import Service.UserService;
import model.User;
import webserver.util.Parser;
import webserver.util.ViewResolver;

import java.util.Collection;

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
            if(stringBody.contains("for::")) {
                Collection<User> users = userService.findAll();
                int i = 1;
                String subString = stringBody.substring(stringBody.indexOf("for::"), stringBody.indexOf("::for"));
                for (User u : users) {
                    String repeatPart = subString;
                    repeatPart = repeatPart.replace(":i:", i + "");
                    repeatPart = repeatPart.replace(":model.userId:", u.getUserId());
                    repeatPart = repeatPart.replace(":model.name:", u.getName());
                    repeatPart = repeatPart.replace(":model.email:", u.getEmail());

                    stringBuilder.append(repeatPart);
                    i++;
                }
                System.out.println(stringBuilder);
                stringBody = stringBody.replace(subString, stringBuilder.toString());
                stringBody = stringBody.replaceAll("for::", "");
                stringBody = stringBody.replaceAll("::for", "");
            }
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
