package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.web.exception.FoundException;
import support.web.HttpMethod;
import support.web.ModelAndView;

@Controller(value = "/")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(method = HttpMethod.GET)
    public ModelAndView index() throws FoundException {
        logger.debug("인덱스 요청");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }

}
