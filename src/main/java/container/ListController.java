package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.HOME_PATH;

public class ListController implements Controller {
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (request.getHeader("Cookie") == null) {
            return HOME_PATH.getPath();
        }
        return "/user/list.html";
    }
}
