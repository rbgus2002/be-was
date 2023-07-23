package webserver.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.message.*;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.message.HttpHeaders.CONTENT_TYPE;


class StaticFileHandlerTest {
    StaticFileHandler staticFileHandler;

    @BeforeEach
    void init() {
        staticFileHandler = new StaticFileHandler();
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
        verifyContentTypeWithAccept("/images/80-text.png", "image/png");
        verifyContentTypeWithAccept("/fonts/glyphicons-halflings-regular.eot", "application/vnd.ms-fontobject");
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
        verifyContentTypeWithOutAccept("/images/80-text.png", "image/png");
        verifyContentTypeWithOutAccept("/fonts/glyphicons-halflings-regular.eot", "application/vnd.ms-fontobject");
    }

    private void verifyContentTypeWithAccept(String path, String contentType) {
        HttpRequest staticRequest = getStaticRequest(path, contentType);

        HttpResponse httpResponse = staticFileHandler.handle(staticRequest);
        HttpHeaders headers = httpResponse.getHttpHeaders();

        assertThat(headers.getSingleValue(CONTENT_TYPE)).isEqualTo(contentType);
    }

    private void verifyContentTypeWithOutAccept(String path, String contentType) {
        HttpRequest staticRequest = getStaticRequestWithOutAccept(path);

        HttpResponse httpResponse = staticFileHandler.handle(staticRequest);
        HttpHeaders headers = httpResponse.getHttpHeaders();

        assertThat(headers.getSingleValue(CONTENT_TYPE)).isEqualTo(contentType);
    }

    private HttpRequest getStaticRequest(String path, String contentType) {
        URL url = URL.from(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(path, contentType, "*/*");
        return new HttpRequest(HttpMethod.GET, url, HttpVersion.V1_1, httpHeaders, null);
    }

    private HttpRequest getStaticRequestWithOutAccept(String path) {
        URL url = URL.from(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(path, "*/*");
        return new HttpRequest(HttpMethod.GET, url, HttpVersion.V1_1, httpHeaders, null);
    }
}
