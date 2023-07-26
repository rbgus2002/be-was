package application.controller;

import application.dto.UserDto;
import application.repositiory.UserRepository;
import exception.badRequest.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import webserver.Constants.HeaderField;
import webserver.Constants.HttpMethod;
import webserver.Constants.HttpStatus;
import webserver.ModelAndView;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.request.RequestQuery;
import webserver.response.HttpResponse;

import java.nio.charset.StandardCharsets;

@Controller(value = "/user")
public class UserController implements WebController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserRepository userRepository = UserRepository.USER_REPOSITORY;

    @RequestMapping(method = HttpMethod.POST, value = "/user/create")
    public ModelAndView createUser(final HttpRequest request, final HttpResponse response) throws MissingParameterException {
        RequestBody requestBody = request.getRequestBody();

        String userId = requestBody.getValue("userId");
        String password = requestBody.getValue("password");
        String name = requestBody.getValue("name");
        String email = requestBody.getValue("email");

        UserDto userDto = new UserDto.Builder()
                .withUserId(userId)
                .withPassword(password)
                .withName(name)
                .withEmail(email)
                .build();

        userRepository.addUser(userDto);
        logger.debug("{}라는 이름의 유저가 생성되었습니다.", name);

        response.setHttpStatus(HttpStatus.SEE_OTHER);
        response.addHeaderElement(HeaderField.Location, "/index.html");
        response.setBody(HttpStatus.SEE_OTHER.getDescription().getBytes(StandardCharsets.UTF_8));
        return new ModelAndView("redirect:", null);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/user/search")
    public ModelAndView getUser(HttpRequest request) throws MissingParameterException {
        RequestQuery requestQuery = request.getRequestQuery();

        String userId = requestQuery.getValue("userId");
        UserDto userDto = userRepository.findUserById(userId);

        // TODO: 나중에 model 에 userDto 정보 넣어서 return 해야 한다.
        return new ModelAndView("/index.html", null);
    }
}

