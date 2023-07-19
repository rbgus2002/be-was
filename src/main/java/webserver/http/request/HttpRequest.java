package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.Parser;

import java.util.Map;

import static exception.ExceptionMessage.URL_NOT_CORRECT;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod method;
    private final String url;
    private final String version;
    private FormData formData;
    public final String BLANK = " ";

    public HttpRequest(String request, String requestBody) {
        String[] req = request.split(BLANK);
        this.formData = null;
        ValidRequest(req);
        method = HttpMethod.valueOf(req[0]);
        String tmpUrl = req[1];
        String[] parseUrlData = Parser.parseUrlData(tmpUrl, method, requestBody);
        if(parseUrlData[0] != null) {
            this.formData = new FormData(parseUrlData[0]);
        }
        this.url = parseUrlData[1];

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
        return formData == null?null:formData.getFormDataMap();
    }
}
