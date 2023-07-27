package container;

import annotation.RequestMapping;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
@RequestMapping(path = "/qna/form")
public class WriteController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        return null;
    }
}
