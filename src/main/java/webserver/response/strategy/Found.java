package webserver.response.strategy;

import webserver.Header;

public class Found implements ResponseHeaderStrategy {

    private final String redirectionUrl;

    public Found(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    @Override
    public void buildHeader(Header header) {
        header.appendHeader("Location", redirectionUrl);
    }

}
