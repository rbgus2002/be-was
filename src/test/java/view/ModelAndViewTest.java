package view;

import model.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ModelAndViewTest {

    ModelAndView mv;

    @BeforeEach
    void setUp() {
        mv = new ModelAndView();
    }

    @Test
    @DisplayName("view 추가 후 확인")
    void setViewTest() {
        String view = "/index.html";

        mv.setViewName(view);

        assertEquals(view, mv.getView());
    }

    @Test
    @DisplayName("model 추가 후 확인")
    void setModelTest() {
        mv.addObject("name", "jwk");
        mv.addObject("age", 25);

        Map<String, Object> model = mv.getModelMap();

        assertEquals("jwk", model.get("name"));
        assertEquals(25, model.get("age"));
    }

    @Test
    @DisplayName("status코드 설정 후 확인")
    void setContentTypeTest() {
        mv.setContentType(ContentType.TEXT_PLAIN);

        assertEquals(ContentType.TEXT_PLAIN.getContentType(), mv.getContentType().getContentType());
    }

    //TODO: response 잘 뱉는지 테스트하는 코드 작성해야함
    @Test
    void setResponse() {
    }
}