package webserver.controllers;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;

class FrontControllerTest {
    FrontController frontController = FrontController.getInstance();
    SoftAssertions softly = new SoftAssertions();

    @Test
    @DisplayName("request의 path에 따라 라우팅이 잘 되는지 확인한다.")
    void checkRequestResolve() {
        HttpRequest.Builder builder = new HttpRequest.Builder();
        HttpRequest request1 = builder.uri("/user/create").build();
        HttpRequest request2 = builder.uri("/index.html").build();
        HttpRequest request3 = builder.uri("/test.path").build();
        HttpRequest request4 = builder.uri("/user/create?userId=1234&password=password").build();

        softly.assertThat(frontController.resolveRequest(request1).getClass()).isEqualTo(UserCreateController.class);
        softly.assertThat(frontController.resolveRequest(request2).getClass()).isEqualTo(StaticFileController.class);
        softly.assertThat(frontController.resolveRequest(request3).getClass()).isEqualTo(StaticFileController.class);
        softly.assertThat(frontController.resolveRequest(request4).getClass()).isEqualTo(UserCreateController.class);
    }

}