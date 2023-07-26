package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
@RequestMapping(path = "/user/profile")
public class ProfileController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        return "/user/profile.html";
    }
}
