package webserver.http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.response.header.HttpContentType;

import java.lang.reflect.Field;
import java.util.Map;


class HttpContentTypeTest {


    @Test
    @DisplayName("맵이 잘 되는지?")
    void do_right() throws NoSuchFieldException, IllegalAccessException {
        HttpContentType httpContentType = HttpContentType.createHttpContentType();
        Class<HttpContentType> clazz = HttpContentType.class;
        Field field = clazz.getDeclaredField("extension");
        field.setAccessible(true);
        Map<String, String> map = (Map<String, String>) field.get(httpContentType);
        SoftAssertions softAssertions = new SoftAssertions();
        for(String key : map.keySet()) {
            softAssertions.assertThat(map.get(key)).isEqualTo(httpContentType.getContentType(key));
        }
        softAssertions.assertAll();
    }
}