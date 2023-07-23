package controller;

import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static controller.HomeController.HOME_CONTROLLER;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    HttpController controller;
    Map<String, String> requestParams;
    HttpResponse response;

    @BeforeEach
    void setUp() {
        controller = HOME_CONTROLLER;
        requestParams = new HashMap<>();
        response = new HttpResponse();
    }

    @Test
    @DisplayName("/index.html을 반환해야 한다.")
    void process() {
        assertEquals("/index.html", controller.process(requestParams, response));
    }
}