package webserver.handlers;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.message.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileHandlerTest {
    StaticFileHandler staticFileHandler;
    SoftAssertions softAssertions;

    @BeforeEach
    void init() {
        staticFileHandler = new StaticFileHandler();
        softAssertions = new SoftAssertions();
    }

    @DisplayName("Accept 정보를 통해 적절한 Content-Type 을 담은 Repsonse 반환")
    @Test
    void handleStaticRequestTest() {
        verifyContentTypeWithAccept("/favicon.ico", "image/x-icon");
        verifyContentTypeWithAccept("/css/styles.css", "text/css");
        verifyContentTypeWithAccept("/css/bootstrap.min.css", "text/css");
        verifyContentTypeWithAccept("/js/jquery-2.2.0.min.js", "application/javascript");
        verifyContentTypeWithAccept("/js/scripts.js", "application/javascript");
        verifyContentTypeWithAccept("/js/bootstrap.min.js", "application/javascript");
        verifyContentTypeWithAccept("/images/80-text.png", "application/javascript");
        verifyContentTypeWithAccept("/fonts/glyphicons-halflings-regular.eot", "application/vnd.ms-fontobject");
        verifyContentTypeWithAccept("/fonts/glyphicons-halflings-regular.ttf", "application/font-sfnt");
        verifyContentTypeWithAccept("/fonts/glyphicons-halflings-regular.svg", "image/svg+xml");
        verifyContentTypeWithAccept("/fonts/glyphicons-halflings-regular.woff", "application/font-woff");
    }

    @DisplayName("Accept가 */*이라면, URL에 포함된 파일 확장자를 통해 적절한 Content-Type을 담은 Response 반환")
    @Test
    void handleStaticRequestWithOutAcceptTest() {
        verifyContentTypeWithOutAccept("/favicon.ico", "image/x-icon");
        verifyContentTypeWithOutAccept("/css/styles.css", "text/css");
        verifyContentTypeWithOutAccept("/css/bootstrap.min.css", "text/css");
        verifyContentTypeWithOutAccept("/js/jquery-2.2.0.min.js", "application/javascript");
        verifyContentTypeWithOutAccept("/js/scripts.js", "application/javascript");
        verifyContentTypeWithOutAccept("/js/bootstrap.min.js", "application/javascript");
        verifyContentTypeWithOutAccept("/images/80-text.png", "application/javascript");
        verifyContentTypeWithOutAccept("/fonts/glyphicons-halflings-regular.eot", "application/vnd.ms-fontobject");
        verifyContentTypeWithOutAccept("/fonts/glyphicons-halflings-regular.ttf", "application/font-sfnt");
        verifyContentTypeWithOutAccept("/fonts/glyphicons-halflings-regular.svg", "image/svg+xml");
        verifyContentTypeWithOutAccept("/fonts/glyphicons-halflings-regular.woff", "application/font-woff");
    }

    private void verifyContentTypeWithAccept(String path, String contentType) {
        HttpRequest staticRequest = getStaticRequest(path, contentType);

        HttpResponse httpResponse = staticFileHandler.handle(staticRequest);

        softAssertions.assertThat(httpResponse.getMetaData()).containsEntry("Content-Type", List.of(contentType));
    }

    private void verifyContentTypeWithOutAccept(String path, String contentType) {
        HttpRequest staticRequest = getStaticRequestWithOutAccept(path);

        HttpResponse httpResponse = staticFileHandler.handle(staticRequest);

        softAssertions.assertThat(httpResponse.getMetaData()).containsEntry("Content-Type", List.of(contentType));
    }

    private HttpRequest getStaticRequest(String path, String contentType) {
        URL url = URL.from(path);
        Map<String, List<String>> httpHeaders = new HashMap<>();
        httpHeaders.put(path, List.of(contentType, "*/*"));
        return new HttpRequest(HttpMethod.GET, url, HttpVersion.V1_1, httpHeaders, null);
    }

    private HttpRequest getStaticRequestWithOutAccept(String path) {
        URL url = URL.from(path);
        Map<String, List<String>> httpHeaders = new HashMap<>();
        httpHeaders.put(path, List.of("*/*"));
        return new HttpRequest(HttpMethod.GET, url, HttpVersion.V1_1, httpHeaders, null);
    }
}
