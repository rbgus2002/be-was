package view.renderer;

import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListRenderer extends HtmlRenderer {

    private final String USER_INFO_FORMAT =
            "<th scope=\"row\">%s</th>" +
                "<td>%s</td> <td>%s</td> <td>%s</td>" +
            "<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>";

    @Override
    public String decorate(String html, Map<String, Object> model) {
        StringBuilder tableBuilder = new StringBuilder();

        List<User> users = getUserList(model);

        int index = 1;
        tableBuilder.append("<tbody>");
        for (User user : users) {
            tableBuilder.append("<tr>");
            tableBuilder.append(
                    String.format(USER_INFO_FORMAT, index++, user.getUserId(), user.getName(), user.getEmail())
            );
            tableBuilder.append("</tr>");
        }
        tableBuilder.append("</tbody>");

        return replaceTag(html, "userInfo", tableBuilder.toString());
    }

    private List<User> getUserList(Map<String, Object> model) {
        List<User> users = new ArrayList<>();

        List<?> temps = (List<?>) model.get("users");
        if (temps != null) {
            for (Object temp : temps) {
                users.add((User) temp);
            }
        }

        return users;
    }

}
