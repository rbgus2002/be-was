package webserver.response.strategy;

import webserver.Header;

public class OK implements ResponseHeaderStrategy {

    private final String contentType;
    private final int lengthOfBodyContent;

    public OK(String contentType, int lengthOfBodyContent) {
        this.contentType = contentType;
        this.lengthOfBodyContent = lengthOfBodyContent;
    }

    @Override
    public void buildHeader(Header header) {
        header.appendHeader("Content-Type", contentType)
                .appendHeader("Content-Length", String.valueOf(lengthOfBodyContent));
    }

}
