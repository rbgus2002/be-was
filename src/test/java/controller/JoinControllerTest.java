package controller;

import db.Database;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static controller.JoinController.JOIN_CONTROLLER;

class JoinControllerTest {

    HttpController controller;
    Map<String, String> requestParams;

    @BeforeEach
    void setUp() {
        controller = JOIN_CONTROLLER;
        requestParams = new HashMap<>();
    }

    @Test
    @DisplayName("회원가입 후 redirect:/index.html을 반환해야 한다.")
    void process() {
        requestParams.put("userId", "server");
        requestParams.put("password", "password");
        requestParams.put("name", "hello");
        requestParams.put("email", "server@example.com");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(controller.process(requestParams)).isEqualTo("redirect:/index.html");
        softly.assertThat(Database.findAll().size()).isEqualTo(1);
        softly.assertAll();
    }

}