package webserver.reponse;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    HttpResponse httpResponse;

    @BeforeEach
    void init() {
        httpResponse = new HttpResponse();
    }

    @Test
    @DisplayName("response 헤더명과 헤더값을 넣으면 이를 key, value 쌍으로 저장해야 한다.")
    void header() {
        httpResponse.setStatus(HttpResponseStatus.STATUS_200);
        httpResponse.setHeader("Host", "localhost:8080");
        httpResponse.setHeader("Connection", "keep-alive");
        httpResponse.setHeader("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        httpResponse.setHeader("sec-ch-ua-mobile", "?0");
        httpResponse.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
    }
}