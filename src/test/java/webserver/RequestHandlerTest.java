package webserver;

import db.Database;
import model.User;
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

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 200 OK", result[0]);
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

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 404 Not Found", result[0]);
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

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 200 OK", result[0]);
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

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 200 OK", result[0]);
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

            //then
            assertEquals("javajigi", user.getUserId());
            assertEquals("password", user.getPassword());
            assertEquals("%EB%B0%95%EC%9E%AC%EC%84%B1", user.getName());
            assertEquals("javajigi%40slipp.net", user.getEmail());

            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 302 Found", result[0]);
            assertEquals("Location: " + MAIN_PAGE, result[1]);
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

            //then
            assertNull(user);

            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 400 Bad Request", result[0]);
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

            //then
            assertEquals("javajigi", user.getUserId());
            assertEquals("password", user.getPassword());
            assertEquals("%EB%B0%95%EC%9E%AC%EC%84%B1", user.getName());
            assertEquals("javajigi%40slipp.net", user.getEmail());

            String[] result = outputStream.toString().split(NEW_LINE);
            assertEquals("HTTP/1.1 302 Found", result[0]);
            assertEquals("Location: " + MAIN_PAGE, result[1]);
        }

    }

}
