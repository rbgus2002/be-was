package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

public interface Controller {
    String process(HTTPServletRequest request, HTTPServletResponse response);
}
