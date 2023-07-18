package webserver.http.response;

import webserver.util.HeaderParser;
import webserver.util.IOUtils;

public class ResponseBody {
    private final byte[] body;

    public ResponseBody(String url) {
        body = IOUtils.getContent(url, HeaderParser.getUrlExtension(url));
    }

    public int getLength() {
        return body == null ?0:body.length;
    }

    public byte[] readBody() {
        return body;
    }
}
