package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HeaderParser;

import java.util.Map;

import static exception.ExceptionMessage.URL_NOT_CORRECT;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod method;
    private final String url;
    private final String version;
    private FormData formData;

    public HttpRequest(String request, String requestBody) {
        String[] req = request.split(" ");
        ValidRequest(req);
        method = HttpMethod.valueOf(req[0]);
        String tmpUrl = req[1];

        //todo: 여기 부분 분리
        if(tmpUrl.contains("?")) {
            if(method.equals(HttpMethod.GET)) {
                this.formData = new FormData(HeaderParser.parseQuery(tmpUrl));
            }
                this.url = HeaderParser.parseUrl(tmpUrl);
        } else {
            this.url = tmpUrl;
        }

        if(method.equals(HttpMethod.POST)) {
            this.formData = new FormData(requestBody);
        }
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }


    private void ValidRequest(String[] req) {
        if (req.length != 3) {
            throw new RuntimeException(URL_NOT_CORRECT);
        }
    }
    public HttpMethod getMethod() {
        return method;
    }
    public String getUrl() {
        return url;
    }
    public String getVersion() {
        return version;
    }
    public Map<String, String> getFormDataMap() {
        return formData.getFormDataMap();
    }
}
