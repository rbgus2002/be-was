package global.annotation;

import annotations.GetMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GetMapping 어노테이션 테스트")
class GetMappingTest {

    @Test
    @DisplayName("GetMapping 어노테이션 테스트")
    void testMyGetMappingAnnotation() throws NoSuchMethodException {
        //given
        String expectedPath = "/index.html";

        //when
        Method method = TestController.class.getMethod("getIndex");
        GetMapping annotation = method.getAnnotation(GetMapping.class);
        String path = annotation.path();

        //then
        assertEquals(expectedPath, path);
    }

    static class TestController {
        @GetMapping(path = "/index.html")
        public void getIndex() {
            // Controller method
        }
    }
}
