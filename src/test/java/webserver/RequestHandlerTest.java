package webserver;

import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.NEW_LINE;
import static utils.StringUtils.appendNewLine;
import static webserver.HttpHandler.MAIN_PAGE;

@DisplayName("RequestHandler 테스트")
class RequestHandlerTest {

    OutputStream outputStream = new ByteArrayOutputStream();
    SoftAssertions softAssertions;

    String OK = "HTTP/1.1 200 OK";
    String BAD_REQUEST = "HTTP/1.1 400 Bad Request";
    String NOT_FOUND = "HTTP/1.1 404 Not Found";

    static class IoSocket extends Socket {

        private InputStream inputStream;
        private OutputStream outputStream;

        public IoSocket() {
        }

        public void changeStream(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return outputStream;
        }

    }

    @BeforeEach
    void setUp() {
        Database.clear();
        softAssertions = new SoftAssertions();
    }

    RequestHandler buildRequestHandler(String requestLine) {
        IoSocket socket = new IoSocket();
        String request = appendNewLine(requestLine, "Host: localhost", "", "");
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        socket.changeStream(inputStream, outputStream);
        return new RequestHandler(socket);
    }

    @Nested
    @DisplayName("단순 페이지 로드 테스트")
    class getPage {

        @Test
        @DisplayName("페이지를 요청했을 경우 해당 페이지를 반환해야 한다.")
        void test() {
            //given
            String request = "GET /index.html HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            String response = outputStream.toString();
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            assertEquals(OK, result[0]);
        }

        @Test
        @DisplayName("잘못된 파일 요청 테스트")
        void wrongPath() {
            //given
            String request = "GET /noindex.html HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            String response = outputStream.toString();
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            assertEquals(NOT_FOUND, result[0]);
        }

        @Test
        @DisplayName("MIME에 따라 다양한 파일 타입에 대해 contentType을 다르게 적용해주어야 한다.")
        void contextType() {
            //given
            String request = "GET /css/bootstrap.min.css HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            String response = outputStream.toString();
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            assertEquals(OK, result[0]);
        }

        @Test
        @DisplayName("MIME에 따라 다양한 파일 타입에 대해 contentType을 다르게 적용해주어야 한다.")
        void contextTypeJavaScript() {
            //given
            String request = "GET /js/jquery-2.2.0.min.js HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            String response = outputStream.toString();
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            assertEquals(OK, result[0]);
        }

    }

    @Nested
    @DisplayName("유저 생성 테스트")
    class Create {

        @Test
        @DisplayName("새로운 유저 등록 요청 처리 테스트")
        void registerUser() {
            //given
            String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            softAssertions.assertThat(user.getUserId()).isEqualTo("javajigi");
            softAssertions.assertThat(user.getPassword()).isEqualTo("password");
            softAssertions.assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            softAssertions.assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
            softAssertions.assertThat(result[0]).isEqualTo("HTTP/1.1 302 Found");
            softAssertions.assertThat(result[1]).isEqualTo("Location: " + MAIN_PAGE);
            softAssertions.assertAll();

        }

        @Test
        @DisplayName("다른(누락) 쿼리 등록 요청 처리 테스트")
        void registerUserDiff() {
            //given
            String request = "GET /user/create?userId=javajigi&pass=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            assertNull(user);
            assertEquals(BAD_REQUEST, result[0]);
        }

        @Test
        @DisplayName("다른 순서의 쿼리 등록 요청 처리 테스트")
        void registerDiffSequence() {
            //given
            String request = "GET /user/create?password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net&userId=javajigi HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(NEW_LINE);

            //then
            softAssertions.assertThat(user.getUserId()).isEqualTo("javajigi");
            softAssertions.assertThat(user.getPassword()).isEqualTo("password");
            softAssertions.assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            softAssertions.assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
            softAssertions.assertThat(result[0]).isEqualTo("HTTP/1.1 302 Found");
            softAssertions.assertThat(result[1]).isEqualTo("Location: " + MAIN_PAGE);
            softAssertions.assertAll();
        }

    }

}
