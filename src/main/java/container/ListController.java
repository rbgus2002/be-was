package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;

public class ListController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        return "/user/list.html";
    }
}
