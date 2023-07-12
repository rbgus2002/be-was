package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerTest {

    @Test
    @DisplayName("싱글톤으로 생성된다.")
    void createSingleton() {
        //given, when
        Controller c1 = Controller.getInstance();
        Controller c2 = Controller.getInstance();

        //then
        assertThat(c1).isEqualTo(c2);
    }
}