package view;

import webserver.HTTPServletRequest;

import java.io.IOException;

public interface ViewBase {

    String changeToDynamic(HTTPServletRequest request) throws IOException;
}
