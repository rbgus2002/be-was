package webserver;

import controller.UserController;
import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import support.instance.DefaultInstanceManager;
import support.web.HttpMethod;
import support.web.handler.ControllerMethodReturnValueHandlerComposite;
import support.web.view.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.CRLF;
import static utils.StringUtils.appendNewLine;

@DisplayName("RequestHandler 테스트")
class RequestHandlerTest {

    OutputStream outputStream = new ByteArrayOutputStream();
    SoftAssertions softAssertions;

    final String OK = "HTTP/1.1 200 OK";
    final String Found = "HTTP/1.1 302 Found";
    final String BAD_REQUEST = "HTTP/1.1 400 Bad Request";
    final String NOT_FOUND = "HTTP/1.1 404 Not Found";
    final String METHOD_NOT_ALLOWED = "HTTP/1.1 405 Method Not Allowed";

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

    @BeforeAll
    static void prepare() throws NoSuchFieldException, IllegalAccessException {
        DefaultInstanceManager defaultInstanceManager = new DefaultInstanceManager();
        Field instance = DefaultInstanceManager.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(null, defaultInstanceManager);
        defaultInstanceManager.addInstance("UserController", new UserController());
        defaultInstanceManager.addInstance("ViewContainer", new ViewContainer(
                new ErrorView(), new IndexView(), new PostShowView(), new UserListView()
        ));
        defaultInstanceManager.addInstance("HttpHandler", new HttpHandler());
        defaultInstanceManager.addInstance("ControllerMethodReturnValueHandlerComposite", new ControllerMethodReturnValueHandlerComposite());
    }

    @BeforeEach
    void setUp() {
        Database.clear();
        softAssertions = new SoftAssertions();
    }

    RequestHandler buildRequestHandler(String requestLine) {
        return buildRequestHandler(requestLine, "");
    }

    RequestHandler buildRequestHandler(String requestLine, String body) {
        IoSocket socket = new IoSocket();
        String request = appendNewLine(requestLine, "Host: localhost", "Content-Length: " + body.length(), "", body);
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
            String[] result = outputStream.toString().split(CRLF);

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
            String[] result = outputStream.toString().split(CRLF);

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
            String[] result = outputStream.toString().split(CRLF);

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
            String[] result = outputStream.toString().split(CRLF);

            //then
            assertNotNull(response);
            assertNotEquals(0, response.length());
            assertEquals(OK, result[0]);
        }

    }

    @Nested
    @DisplayName("유저 생성 테스트")
    class Create {

        private final String method = "POST";

        @Test
        @Disabled
        @DisplayName("GET, POST 메소드 분리 테스트")
        void methodSeparate() {
            //given
            String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request);

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(CRLF);

            //then
            softAssertions.assertThat(user).isNull();
            softAssertions.assertThat(result[0]).isEqualTo(METHOD_NOT_ALLOWED);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("새로운 유저 등록 요청 처리 테스트")
        void registerUser() {
            //given
            String request = method + " /user/create HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net&userId=javajigi");

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(CRLF);

            //then
            softAssertions.assertThat(user.getUserId()).isEqualTo("javajigi");
            softAssertions.assertThat(user.getPassword()).isEqualTo("password");
            softAssertions.assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            softAssertions.assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
            softAssertions.assertThat(result[0]).isEqualTo(Found);
            softAssertions.assertAll();

        }

        @Test
        @DisplayName("다른(누락) 쿼리 등록 요청 처리 테스트")
        void registerUserDiff() {
            //given
            String request = method + " /user/create HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "pass=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net&userId=javajigi");

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(CRLF);

            //then
            assertNull(user);
            assertEquals(BAD_REQUEST, result[0]);
        }

        @Test
        @DisplayName("다른 순서의 쿼리 등록 요청 처리 테스트")
        void registerDiffSequence() {
            //given
            String request = method + " /user/create HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net&userId=javajigi");

            //when
            requestHandler.run();
            User user = Database.findUserById("javajigi");
            String[] result = outputStream.toString().split(CRLF);

            //then
            softAssertions.assertThat(result[0]).isEqualTo(Found);
            softAssertions.assertThat(user).isNotNull();
            softAssertions.assertAll();

            softAssertions.assertThat(user.getUserId()).isEqualTo("javajigi");
            softAssertions.assertThat(user.getPassword()).isEqualTo("password");
            softAssertions.assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            softAssertions.assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
            softAssertions.assertThat(result[0]).isEqualTo(Found);
            softAssertions.assertAll();
        }

    }

    @Nested
    @DisplayName("유저 로그인 시도")
    class login {

        @Test
        @DisplayName("로그인 성공 시도")
        void success() {
            //given
            Database.addUser(new User("userId", "password", "name", "email@luiso.xgv"));
            String request = HttpMethod.POST.name() + " /user/login HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "userId=userId&password=password");

            //when
            requestHandler.run();
            String[] result = outputStream.toString().split(CRLF);

            //then
            softAssertions.assertThat(result[0]).isEqualTo(Found);
            softAssertions.assertThat(Arrays.stream(result).anyMatch("Location"::startsWith)).isNotEqualTo("");
            softAssertions.assertThat(Arrays.stream(result).anyMatch("Set-cookie"::startsWith)).isNotEqualTo("");
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("로그인 실패 시도")
        void failure() {
            //given
            Database.addUser(new User("userId", "password", "name", "email@luiso.xgv"));
            String request = HttpMethod.POST.name() + " /user/login HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "userId=userId&password=wrongpassword");

            //when
            requestHandler.run();
            String[] result = outputStream.toString().split(CRLF);

            //then
            assertThat(result[0]).isEqualTo(Found);
            assertThat(result[1]).isEqualTo("Location: /user/login_failed.html");
        }

        @Test
        @DisplayName("없는 유저 로그인 실패 시도")
        void failure2() {
            //given
            String request = HttpMethod.POST.name() + " /user/login HTTP/1.1";
            RequestHandler requestHandler = buildRequestHandler(request, "userId=userId&password=password");

            //when
            requestHandler.run();
            String[] result = outputStream.toString().split(CRLF);

            //then
            assertThat(result[0]).isEqualTo(Found);
            assertThat(result[1]).isEqualTo("Location: /user/login_failed.html");
        }

    }

}
