package webserver.fixture;

import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpVersion;
import webserver.http.message.URL;

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
        return new HttpRequest(HttpMethod.GET, URL.from(url), HttpVersion.V1_1, null, null);
    }
}
