package view;

import webserver.HTTPServletRequest;

import java.io.IOException;

public interface ViewBase {

    public String changeToDynamic(HTTPServletRequest request) throws IOException;
}
