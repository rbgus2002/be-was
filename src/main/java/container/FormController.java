package container;

import annotation.RequestMapping;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

@RequestMapping(path = "/user/form")
public class FormController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response){
        return "/user/form.html";
    }
}
