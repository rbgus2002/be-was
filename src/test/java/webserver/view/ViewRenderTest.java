package webserver.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ViewRenderTest {
    @Test
    void parseHtml() {
        String html = "<li><a href=\"rend:{href:user/login.html}\" role=\"button\">rend:{userId:로그인}</a></li>";

        Map<String, String> matchedData = new HashMap<>();
        matchedData.put("userId", "meme");
        matchedData.put("href", "#");
        Assertions.assertEquals("<li><a href=\"#\" role=\"button\">meme</a></li>", ViewRender.renderWord(html, matchedData));
    }
}
