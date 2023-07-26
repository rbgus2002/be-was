package webserver.fixture;

import webserver.http.message.*;

public class HttpRequestFixture {
    public static HttpRequest getRequestIndex() {
        return getHttpRequest("/index.html");
    }

    public static HttpRequest getRequestUserForm() {
        return getHttpRequest("/user/form.html");
    }

    public static HttpRequest getStrangeRequest() {
        return getHttpRequest("/weired_URL_HAHA");
    }

    private static HttpRequest getHttpRequest(String url) {
        return new HttpRequest(HttpMethod.GET, URL.from(url), HttpVersion.V1_1, new HttpHeaders(), null);
    }
}
