package webserver.handlers;

import exception.UserServiceException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.session.Session;
import webserver.model.Model;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private static final TemplateRenderer templateRender = TemplateRenderer.getInstance();
    public static final String LOGIN_SUCCESS = "/index.html";
    public static final String AND = "&";
    public static final String EQUAL = "=";
    public static final String LOGIN_FAILED = "/user/login_failed.html";
    private final UserService userService;

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        try {
            Map<String, String> body = getBody(request);
            String loginUserId = body.get("userId");
            String loginPassword = body.get("password");
            User loginUser = userService.login(loginUserId, loginPassword);
            HttpResponse response = HttpResponse.redirect(LOGIN_SUCCESS);
            session.setUser(loginUser);
            return response;
        } catch (NullPointerException e) {
            logger.warn("bad request : {}", e.getMessage());
            return HttpResponse.badRequest();
        } catch (UserServiceException e) {
            logger.warn("login Fail : {}", e.getMessage());
            return responseFail();
        }
    }

    private HttpResponse responseFail() {
        Model model = new Model();
        byte[] file = FileUtils.readFileFromTemplate(LOGIN_FAILED);
        model.setAttribute("loginStatus", "false");
        String html = templateRender.render(new String(file), model);
        return HttpResponse.badRequestWithFile(html.getBytes(), Mime.HTML);
    }

    private Map<String, String> getBody(HttpRequest request) {
        char[] messageBody = request.getBody();
        String body = makeString(messageBody);
        return parseBody(body);
    }

    private String makeString(char[] messageBody) {
        String body = String.valueOf(messageBody);
        return URLDecoder.decode(body, StandardCharsets.UTF_8);
    }

    private static Map<String, String> parseBody(String body) {
        HashMap<String, String> joinInfos = new HashMap<>();
        String[] infos = body.split(AND);
        for (String info : infos) {
            String[] token = info.split(EQUAL);
            joinInfos.put(token[0], token[1]);
        }
        return joinInfos;
    }
}
