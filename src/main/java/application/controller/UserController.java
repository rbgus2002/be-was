package application.controller;

import application.dto.UserDto;
import application.repositiory.UserRepository;
import exception.InvalidQueryParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import webserver.Constants.ContentType;
import webserver.Constants.HttpMethod;
import webserver.Constants.HttpStatus;
import webserver.request.HttpRequest;
import webserver.request.RequestQuery;
import webserver.response.HttpResponse;

@Controller(value = "/user")
public class UserController implements WebController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = UserRepository.USER_REPOSITORY;
    }

    @RequestMapping(method = HttpMethod.POST, value = "/user/create")
    public HttpResponse createUser(final HttpRequest request) throws InvalidQueryParameterException {
        RequestQuery requestQuery = request.getRequestQuery();

        String userId = requestQuery.getValue("userId");
        String password = requestQuery.getValue("password");
        String name = requestQuery.getValue("name");
        String email = requestQuery.getValue("email");

        UserDto userDto = new UserDto.Builder()
                .withUserId(userId)
                .withPassword(password)
                .withName(name)
                .withEmail(email)
                .build();

        userRepository.addUser(userDto);

        return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.CREATED);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/user/search")
    public HttpResponse getUser(HttpRequest request) throws InvalidQueryParameterException {
        RequestQuery requestQuery = request.getRequestQuery();

        String userId = requestQuery.getValue("userId");
        UserDto userDto = userRepository.findUserById(userId);

        return HttpResponse.ofWithBodyData(request.getVersion(), HttpStatus.OK, ContentType.HTML, userDto.toString().getBytes());
    }
}

