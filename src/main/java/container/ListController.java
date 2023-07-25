package container;

import util.PathList;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;

import static util.PathList.*;

public class ListController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        if (request.getHeader("Cookie") == null) {
            return HOME_PATH.getPath();
        }
        return "/user/list.html";
    }
}
