package webserver.fixture;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpVersion;
import webserver.http.URL;

public class HttpRequestFixture {
    public static HttpRequest getRequestIndex() {
        return new HttpRequest(HttpMethod.GET, URL.from("/index.html"), HttpVersion.V1_1, null, null);
    }

    public static HttpRequest getStrangeRequest() {
        return new HttpRequest(HttpMethod.GET, URL.from("/afjawifoawef"), HttpVersion.V1_1, null, null);
    }
}
