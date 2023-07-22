package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.BadRequestException;
import webserver.exception.NotFoundException;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FrontControllerTest {

    String badRequestInput = "GET /user/create?id=1&password=1234 HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\n" +
            "sec-ch-ua-mobile: ?0\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\n" +
            "sec-ch-ua-platform: \"macOS\"\n" +
            "Accept: image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Sec-Fetch-Mode: no-cors\n" +
            "Sec-Fetch-Dest: image\n" +
            "Referer: http://localhost:8080/\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
            "\n" +
            "{\n" +
            "  \"name\": \"John Doe\",\n" +
            "  \"email\": \"john@example.com\",\n" +
            "  \"age\": 30\n" +
            "}\n";
    @Test
    @DisplayName("요청과 일치하는 method나 url이 없으면 NotFoundException 반환한다.")
    void NotFoundException() {
        HttpRequest request = new HttpRequest("GET", "/index.qwer", null, null, null);
        HttpResponse response = new HttpResponse();
        assertThrows(NotFoundException.class, () -> FrontController.service(request, response));
    }

    @Test
    @DisplayName("컨트롤러에서 요구하는 요청 값이 제대로 들어있지 않을 경우 BadRequestException을 반환한다.")
    void BadRequestException() throws IOException {
        HttpRequest request = HttpRequestParser.getRequest(new ByteArrayInputStream(badRequestInput.getBytes()));
        HttpResponse response = new HttpResponse();
        assertThrows(BadRequestException.class, () -> FrontController.service(request, response));
    }

}