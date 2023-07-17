package webserver.response.strategy;

import webserver.Header;

public interface ResponseHeaderStrategy {

    void buildHeader(Header header);

}
