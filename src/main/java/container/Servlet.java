package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;

public interface Servlet {

    void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException;
 

}
