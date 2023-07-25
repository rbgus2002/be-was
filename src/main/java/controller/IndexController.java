package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.exception.FoundException;
import support.web.HttpMethod;

@Controller(value = "/")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(method = HttpMethod.GET)
    public String index() throws FoundException {
        logger.debug("인덱스 요청");

        return "/index";
    }

}
