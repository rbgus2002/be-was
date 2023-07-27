package resources;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import webserver.http.model.Request;
import webserver.http.model.Request.Method;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import static webserver.RequestHandler.parseRequest;
import static webserver.http.HttpParser.parseBodyParameter;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = parseRequest(in);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getMethod()).isEqualTo(Method.GET);
        assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertions.assertThat(request.getQueryParameter("userId")).isEqualTo("javajigi");
        assertions.assertAll();
    }
    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        Request request = parseRequest(in);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(request.getMethod()).isEqualTo(Method.POST);
        assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Map<String, String> parameterMap = parseBodyParameter(request.getBody());
        assertions.assertThat(parameterMap.get("userId")).isEqualTo("javajigi");
        assertions.assertAll();
    }
}
