package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class 검색기 테스트")
class ClassListenerTest {

    @Test
    @DisplayName("해당 package 내부의 모든 클래스를 반환해야 한다.")
    void scanner() {
        //given

        //when
        List<Class<?>> classes = ClassListener.scanClass("model");

        //then
        assertTrue(classes.contains(User.class));
    }

    @Test
    @DisplayName("없는 package를 탐색할 경우 빈 리스트를 반환해야 한다.")
    void scanner2() {
        //given

        //when
        List<Class<?>> classes = ClassListener.scanClass("hello");

        //then
        assertEquals(0, classes.size());
    }

    @Test
    @DisplayName("루트 package를 포함하여 순회적으로 탐색하여야 합니다.")
    void scannerRoot() {
        //given

        //when
        List<Class<?>> classes = ClassListener.scanClass("");

        //then
        assertNotEquals(0, classes.size());
    }

}
