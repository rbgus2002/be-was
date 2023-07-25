package webserver.templateEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.model.Model;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

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


    private static String readHtml(String resource) {
        byte[] html = FileUtils.readFileFromTemplate(resource);
        return new String(html);
    }
}
