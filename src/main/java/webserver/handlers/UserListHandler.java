package webserver.handlers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.model.Model;
import webserver.session.Session;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

import java.util.*;

public class UserListHandler implements Handler {
    public static final Logger logger = LoggerFactory.getLogger(UserListHandler.class);

    private final UserService userService;
    private final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    public UserListHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        try {
            if (!session.isValid()) {
                return HttpResponse.redirect("/user/login.html");
            }
            List<Map<String, String>> userInfos = getUserInfos();

            Model model = makeModel(userInfos, session);
            String html = renderHtml(request, model);

            return HttpResponse.okWithFile(html.getBytes(), Mime.HTML);
        } catch (NullPointerException e) {
            logger.warn("bad request : {}", e.getMessage());
            return HttpResponse.badRequest();
        }
    }

    private String renderHtml(HttpRequest request, Model model) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);
        String html = templateRenderer.render(new String(file), model);
        return html;
    }

    private Model makeModel(List<Map<String, String>> userInfos, Session session) {
        Model model = new Model();
        model.setAttribute("userName", session.getUser().getName());
        model.setAttribute("user", userInfos);
        model.setAttribute("loginStatus", "true");
        return model;
    }

    private List<Map<String, String>> getUserInfos() {
        Collection<User> users = userService.findAll();
        List<Map<String, String>> userInfos = new ArrayList<>();
        for (User user : users) {
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", user.getUserId());
            userInfo.put("name", user.getName());
            userInfo.put("email", user.getEmail());
            userInfos.add(userInfo);
        }
        return userInfos;
    }
}
