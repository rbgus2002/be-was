package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


class ForwardControllerTest {

    ForwardController forwardController;

    @Test
    @DisplayName("url을 그대로 반환해야 한다!")
    void home() {
        // given
        String url = "/sss";
        forwardController = new ForwardController();
        HttpRequest httpRequest = new HttpRequest("GET " + url + " HTTP/1.1", null);

        // when
        HttpResponse httpResponse = forwardController.execute(httpRequest, new HttpResponse());
        String s = httpResponse.getToUrl();

        // then
        Assertions.assertEquals(url, s);
    }
}