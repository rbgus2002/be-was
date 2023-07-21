package webserver.controllers;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;

class ResolveControllerTest {
    ResolveController resolveController = ResolveController.getInstance();
    SoftAssertions s = new SoftAssertions();

    @Test
    @DisplayName("request의 path에 따라 라우팅이 잘 되는지 확인한다.")
    void checkRequestResolve() {
        HttpRequest.Builder builder = new HttpRequest.Builder();
        HttpRequest request1 = builder.path("/user/create").build();
        HttpRequest request2 = builder.path("/index.html").build();
        HttpRequest request3 = builder.path("/test.path").build();

        s.assertThat(resolveController.resolveRequest(request1).getClass()).isEqualTo(UserCreateController.class);
        s.assertThat(resolveController.resolveRequest(request2).getClass()).isEqualTo(StaticFileController.class);
        s.assertThat(resolveController.resolveRequest(request3).getClass()).isEqualTo(StaticFileController.class);
    }

}