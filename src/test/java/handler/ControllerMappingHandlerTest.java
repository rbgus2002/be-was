package handler;

import controller.BasicController;
import controller.Controller;
import controller.UserController;
import http.HttpRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static http.HttpMethod.GET;
import static http.HttpMethod.POST;

class ControllerMappingHandlerTest {

    private ControllerMappingHandler controllerMappingHandler;
    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        controllerMappingHandler = ControllerMappingHandler.getInstance();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("uri에 따라 알맞은 controller를 매핑할 수 있어야 한다")
    void mappingControllerTest() {
        // Given
        String uri = "/user/create";
        String body = "userId=kimahhh&password=1234&name=김아현&email=kimahyunn132@gmail.com";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=");
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setBody(body)
                .setHeader(headers)
                .build();

        // When
        Controller controller = controllerMappingHandler.mappingController(httpRequest);

        // Then
        softAssertions.assertThat(controller instanceof UserController)
                .as("알맞은 controller가 매핑되지 않았습니다.\n현재 값: %d", controller instanceof UserController)
                .isEqualTo(true);
        softAssertions.assertThat(controller instanceof BasicController)
                .as("알맞은 controller가 매핑되지 않았습니다.\n현재 값: %d", controller instanceof BasicController)
                .isEqualTo(false);

        // Given
        uri = "/index.html";
        headers = new HashMap<>();
        headers.put("Cookie", "Idea-b0842ea5=7bc847be-5914-47b7-be63-da8616787eb6; SID=");
        httpRequest = new HttpRequest.RequestBuilder(GET, uri, "HTTP/1.1")
                .setHeader(headers)
                .build();

        // When
        controller = controllerMappingHandler.mappingController(httpRequest);

        // Then
        softAssertions.assertThat(controller instanceof BasicController)
                .as("알맞은 controller가 매핑되지 않았습니다.\n현재 값: %d", controller instanceof BasicController)
                .isEqualTo(true);
        softAssertions.assertThat(controller instanceof UserController)
                .as("알맞은 controller가 매핑되지 않았습니다.\n현재 값: %d", controller instanceof UserController)
                .isEqualTo(false);
    }
}