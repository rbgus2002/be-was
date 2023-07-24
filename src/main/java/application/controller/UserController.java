package application.controller;

import application.dto.UserDto;
import application.exception.InvalidQueryParameterException;
import application.repositiory.UserRepository;
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

import java.util.Optional;

@Controller(value = "/user")
public class UserController implements WebController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = UserRepository.USER_REPOSITORY;
    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public HttpResponse createUser(HttpRequest request) {
        Optional<RequestQuery> requestQueryOpt = request.getRequestQuery();

        if (requestQueryOpt.isEmpty()) {
            return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.BAD_REQUEST);
        }

        RequestQuery requestQuery = requestQueryOpt.get();

        try {
            String userId = requestQuery.getValue("userId").orElseThrow(InvalidQueryParameterException::new);
            String password = requestQuery.getValue("password").orElseThrow(InvalidQueryParameterException::new);
            String name = requestQuery.getValue("name").orElseThrow(InvalidQueryParameterException::new);
            String email = requestQuery.getValue("email").orElseThrow(InvalidQueryParameterException::new);

            UserDto userDto = new UserDto.Builder()
                    .withUserId(userId)
                    .withPassword(password)
                    .withName(name)
                    .withEmail(email)
                    .build();

            userRepository.addUser(userDto);

        } catch (InvalidQueryParameterException e) {
            logger.debug(e.getMessage());
            return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.BAD_REQUEST);
        }

        return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.CREATED);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/search")
    public HttpResponse getUser(HttpRequest request) {
        Optional<RequestQuery> requestQueryOpt = request.getRequestQuery();

        if (requestQueryOpt.isEmpty()) {
            return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.BAD_REQUEST);
        }

        RequestQuery requestQuery = requestQueryOpt.get();

        try {
            String userId = requestQuery.getValue("userId").orElseThrow(InvalidQueryParameterException::new);
            Optional<UserDto> userDtoOpt = userRepository.findUserById(userId);
            UserDto userDto = userDtoOpt.orElseThrow(InvalidQueryParameterException::new);

            return HttpResponse.ofWithBodyData(request.getVersion(), HttpStatus.OK, ContentType.HTML, userDto.toString().getBytes());
        } catch (InvalidQueryParameterException e) {
            logger.debug(e.getMessage());
            return HttpResponse.ofWithStatusOnly(request.getVersion(), HttpStatus.NOT_FOUND);
        }
    }
}

