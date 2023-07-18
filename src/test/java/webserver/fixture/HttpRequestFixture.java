package webserver.fixture;

import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpVersion;
import webserver.http.message.URL;

public class HttpRequestFixture {
    public static HttpRequest getRequestIndex() {
        return new HttpRequest(HttpMethod.GET, URL.from("/index.html"), HttpVersion.V1_1, null, null);
    }

    public static HttpRequest getStrangeRequest() {
        return new HttpRequest(HttpMethod.GET, URL.from("/afjawifoawef"), HttpVersion.V1_1, null, null);
    }
}
