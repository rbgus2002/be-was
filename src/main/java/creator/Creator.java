package creator;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public interface Creator {
    public HTTPServletResponse getProperResponse(HTTPServletRequest request) throws IOException;
}
