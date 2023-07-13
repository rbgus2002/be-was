package controller;

import http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileUtils;

import java.io.IOException;
import java.io.InputStream;

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

    @Test
    @DisplayName("index()를 실행하면 HttpResponse를 반환한다.")
    void returnHttpResponse() throws IOException {
        //given
        Controller controller = Controller.getInstance();


        //when
        HttpResponse httpResponse = controller.index();

        //then
        InputStream fileInputStream = FileUtils.class.getResourceAsStream("/templates/index.html");
        byte[] expectedByte = fileInputStream.readAllBytes();
        assertThat(httpResponse.getBody()).isEqualTo(expectedByte);
    }
}