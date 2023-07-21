package global.annotation;

import annotations.MyGetMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MyGetMapping 어노테이션 테스트")
class MyGetMappingTest {

    @Test
    @DisplayName("MyGetMapping 어노테이션 테스트")
    void testMyGetMappingAnnotation() throws NoSuchMethodException {
        //given
        String expectedPath = "/index.html";

        //when
        Method method = TestController.class.getMethod("getIndex");
        MyGetMapping annotation = method.getAnnotation(MyGetMapping.class);
        String path = annotation.path();

        //then
        assertEquals(expectedPath, path);
    }

    static class TestController {
        @MyGetMapping(path = "/index.html")
        public void getIndex() {
            // Controller method
        }
    }
}
