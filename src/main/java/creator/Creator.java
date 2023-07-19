package creator;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;

public interface Creator {
     HTTPServletResponse getProperResponse(HTTPServletRequest request) throws IOException;
}
