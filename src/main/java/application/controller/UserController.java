package application.controller;

import application.dto.UserDto;
import application.repositiory.UserRepository;
import exception.badRequest.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.SessionManager;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import webserver.http.Constants.CookieOption;
import webserver.http.Constants.HeaderOption;
import webserver.http.Constants.HttpMethod;
import webserver.http.Constants.HttpStatus;
import webserver.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestQuery;
import webserver.http.response.HttpResponse;

import java.nio.charset.StandardCharsets;

@Controller(value = "/user")
public class UserController implements WebController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserRepository userRepository = UserRepository.USER_REPOSITORY;
    private final String USERID = "userId";
    private final String PASSWORD = "password";
    private final String NAME = "name";
    private final String EMAIL = "email";

    @RequestMapping(method = HttpMethod.POST, value = "/user/create")
    public ModelAndView createUser(final HttpRequest request, final HttpResponse response) throws MissingParameterException {
        RequestBody requestBody = request.getRequestBody();

        String userId = requestBody.getValue(USERID);
        String password = requestBody.getValue(PASSWORD);
        String name = requestBody.getValue(NAME);
        String email = requestBody.getValue(EMAIL);

        UserDto userDto = new UserDto.Builder()
                .withUserId(userId)
                .withPassword(password)
                .withName(name)
                .withEmail(email)
                .build();

        userRepository.addUser(userDto);
        logger.debug("{}라는 이름의 유저가 생성되었습니다.", name);

        response.setHttpStatus(HttpStatus.SEE_OTHER);
        response.addHeaderElement(HeaderOption.LOCATION, "/index.html");
        response.setBody(HttpStatus.SEE_OTHER.getDescription().getBytes(StandardCharsets.UTF_8));
        return new ModelAndView("redirect:", null);
    }

    @RequestMapping(method = HttpMethod.POST, value = "/user/login")
    public ModelAndView loginUser(final HttpRequest request, final HttpResponse response) throws MissingParameterException {
        RequestBody requestBody = request.getRequestBody();

        String userId = requestBody.getValue(USERID);
        String password = requestBody.getValue(PASSWORD);

        UserDto userdto = userRepository.findUserById(userId);

        response.setHttpStatus(HttpStatus.SEE_OTHER);
        response.addHeaderElement(HeaderOption.LOCATION, "/user/login_failed.html");
        response.setBody(HttpStatus.SEE_OTHER.getDescription().getBytes(StandardCharsets.UTF_8));

        if(userdto.getPassword().equals(password)) {
            logger.debug("{}라는 이름의 유저가 로그인하였습니다.", userdto.getName());
            response.addHeaderElement(HeaderOption.LOCATION, "/index.html");
            response.addCookieOption(CookieOption.SID, SessionManager.createSession(userId));
            response.addCookieOption(CookieOption.PATH, "/");
        }

        return new ModelAndView("redirect:", null);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/user/search")
    public ModelAndView getUser(HttpRequest request) throws MissingParameterException {
        RequestQuery requestQuery = request.getRequestQuery();

        String userId = requestQuery.getValue(USERID);
        UserDto userDto = userRepository.findUserById(userId);

        // TODO: 나중에 model 에 userDto 정보 넣어서 return 해야 한다.
        return new ModelAndView("/index.html", null);
    }
}

