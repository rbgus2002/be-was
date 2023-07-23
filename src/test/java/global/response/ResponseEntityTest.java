package global.response;

import exception.NotFoundExtensionException;
import global.constant.Headers;
import global.constant.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResponseEntity 테스트")
class ResponseEntityTest {

    @Test
    @DisplayName("빌더를 사용하여 응답 생성 테스트")
    void testBuildResponse() {
        //given
        String responseBody = "Hello, World!";
        String expectedResponse = "HTTP/1.1 200 OK \n" +
                "Content-Length: 13 \n" +
                "Content-Type: text/html;charset=utf-8 \n" +
                "\n" +
                "Hello, World!";

        //when
        byte[] response = ResponseEntity
                .responseBody(responseBody.getBytes())
                .build();

        //then
        assertAll(
                () -> assertEquals(response[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(response[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(response[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("빌더를 사용하여 응답 리소스 생성 테스트")
    void testBuildResponseResource() throws IOException {
        //given
        String uri = "/index.html";
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        byte[] response = ResponseEntity
                .statusCode(StatusCode.OK)
                .addHeaders(Headers.LOCATION, uri)
                .responseResource(uri)
                .build();

        //then
        assertAll(
                () -> assertEquals(response[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(response[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(response[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("빌더를 사용하여 응답 리소스를 찾을 수 없는 경우 NotFoundExtensionException 발생하는지 확인")
    void testBuildResponseResourceNotFound() {
        //given
        String uri = "/notfound.html";

        //when&then
        assertThrows(NotFoundExtensionException.class, () -> {
            ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, uri)
                    .responseResource(uri)
                    .build();
        });
    }
}
