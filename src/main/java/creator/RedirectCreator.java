package creator;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.Path.HOME_PATH;

public class RedirectCreator implements Creator {
    @Override
    public HTTPServletResponse getProperResponse(HTTPServletRequest request) {
        return new HTTPServletResponse(null, response302Header());
    }

    private String response302Header() {
        String header = "";
        header += "HTTP/1.1 302 \r\n";
        header += "Location: " + HOME_PATH.getPath() + "\r\n";
        header += "Content-Length: 0" + "\r\n";
        header += "\r\n";
        return header;
    }
}
