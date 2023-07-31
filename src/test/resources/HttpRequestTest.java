package resources;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import router.Router;
import webserver.http.HttpUtil;
import webserver.http.model.Request;
import webserver.http.model.Request.Method;
import webserver.http.model.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpUtil.*;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        // Given
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");

        // When
        Request request = new Request(in);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getMethod()).isEqualTo(Method.GET);
        assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertions.assertAll();
    }

    @Test
    public void request_POST() throws Exception {
        // Given
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");

        // When
        Request request = new Request(in);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getMethod()).isEqualTo(Method.POST);
        assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertions.assertAll();
    }

    @Test
    public void request_POST2() throws Exception {
        // Given
        InputStream in = new FileInputStream(testDirectory + "Http_POST2.txt");

        // When
        Request request = new Request(in);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getMethod()).isEqualTo(Method.POST);
        assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertions.assertThat(request.getParameter("id")).isEqualTo("1");
        assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertions.assertAll();
    }

    @Test
    public void responseForward() throws Exception {
        // Given
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, "/index.html");
        Response response = new Response(STATUS.SEE_OTHER, headerMap, null);

        // When
        response.sendResponse(createOutputStream("HTTP_Forward.txt"));

        // Then
        String body = new String(Files.readAllBytes(Paths.get(testDirectory + "HTTP_Forward.txt")));
        assertThat(body).contains("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        // Given
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, "/user/login.html");
        Response response = new Response(STATUS.SEE_OTHER, headerMap, null);

        // When
        response.sendResponse(createOutputStream("HTTP_Redirect.txt"));

        // Then
        String body = new String(Files.readAllBytes(Paths.get(testDirectory + "HTTP_Redirect.txt")));
        assertThat(body).contains("/user/login.html");
    }

    @Test
    public void responseCookies() throws Exception {
        // Given
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
        headerMap.put(HEADER_SET_COOKIE, HEADER_SESSION_ID + "1234" + HEADER_COOKIE_PATH);
        Response response = new Response(STATUS.SEE_OTHER, headerMap, null);

        // When
        response.sendResponse(createOutputStream("HTTP_Cookie.txt"));

        // Then
        String body = new String(Files.readAllBytes(Paths.get(testDirectory + "HTTP_Cookie.txt")));
        assertThat(body).contains(HEADER_SESSION_ID + "1234" + HEADER_COOKIE_PATH);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(testDirectory + filename);
    }
}
