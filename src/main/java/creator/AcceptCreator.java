package creator;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

public class AcceptCreator implements Creator{
    @Override
    public HTTPServletResponse getProperResponse(HTTPServletRequest request) {
        return null;
    }
}
