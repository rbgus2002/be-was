package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;

public interface Controller {
    String process(HTTPServletRequest request, HTTPServletResponse response) throws IOException;
}
