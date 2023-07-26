package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
@RequestMapping(path = "/user/form")
public class FormController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response){
        return "/user/form.html";
    }
}
