package webserver.templateEngine;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.model.Model;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TemplateRendererTest {
    TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Test
    void changeHeader() {
        String html = "[change: to=\"snipet/header.html\"/]";

        html = templateRenderer.renderChange(html);

        Assertions.assertEquals(readHtml("/snipet/header.html"), html);
    }

    @Test
    void changeNav() {
        String html = "[change: to=\"snipet/navigation.html\"/]";

        html = templateRenderer.renderChange(html);

        Assertions.assertEquals(readHtml("/snipet/navigation.html"), html);
    }


    @Test
    void change() {
        String html = "[change: to=\"snipet/header.html\"/]" + "[change: to=\"snipet/navigation.html\"/]";

        html = templateRenderer.renderChange(html);

        String expectedHtml = readHtml("/snipet/header.html") + readHtml("/snipet/navigation.html");
        Assertions.assertEquals(expectedHtml, html);
    }

    @Test
    void changeIf1() {
        String html = "[if: loginStatus=\"false\"]aaa[/if]";
        Model model = new Model();
        model.setAttribute("loginStatus", "false");

        html = templateRenderer.renderIf(html, model);

        Assertions.assertEquals("aaa", html);
    }

    @Test
    void changeIf2() {
        String html = "[if: loginStatus=\"false\"]aaa[/if]";
        Model model = new Model();
        model.setAttribute("loginStatus", "true");

        html = templateRenderer.renderIf(html, model);

        Assertions.assertEquals("", html);
    }

    @Test
    void changeIf() {
        String html = "xxxxx[if: status1=\"false\"]aaaaa[/if][if: status2=\"true\"]bbbbb[/if]";
        Model model = new Model();
        model.setAttribute("status1", "true");
        model.setAttribute("status2", "true");

        html = templateRenderer.renderIf(html, model);

        Assertions.assertEquals("xxxxxbbbbb", html);
    }


    @Test
    void loadTest() {
        String html = "[load: status/]";
        Model model = new Model();
        model.setAttribute("status", "good");

        html = templateRenderer.renderLoad(html, model);

        Assertions.assertEquals("good", html);
    }

    @Test
    void forTest() {
        String html = "[for: user]\n" +
                "        <th>[index]</th><td>[userId]</td><td>[name]</td><td>[email]</td>>\n" +
                "      [/for]";

        Model model = new Model();
        User user1 = new User("id1", "pw1", "name1", "ee1@ee");
        User user2 = new User("id2", "pw2", "name2", "ee2@ee");
        List<Map<String, String>> users = new ArrayList<>();
        users.add(Map.of("userId", user1.getUserId(), "name", user1.getName(), "email", user1.getEmail()));
        users.add(Map.of("userId", user2.getUserId(), "name", user2.getName(), "email", user2.getEmail()));
        model.setAttribute("user", users);

        html = templateRenderer.renderFor(html, model);

        String expectedHtml = "\n" +
                "        <th>1</th><td>id1</td><td>name1</td><td>ee1@ee</td>>\n" +
                "      \n" +
                "        <th>2</th><td>id2</td><td>name2</td><td>ee2@ee</td>>\n" +
                "      \n";

        Assertions.assertEquals(expectedHtml, html);
    }

    private static String readHtml(String resource) {
        byte[] html = FileUtils.readFileFromTemplate(resource);
        return new String(html);
    }
}
