package container;

import db.Database;
import db.SessionManager;
import model.User;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;

import static util.PathList.FAILED_PATH;
import static util.PathList.HOME_PATH;
public class ControllerGroup {
    @RequestMapping(path = "/user/list")
    public String ListController(HTTPServletRequest request, HTTPServletResponse response){
        if (isLogInStatus(request)) {
            return HOME_PATH.getPath();
        }
        return "/user/list.html";
    }

    @RequestMapping(path = "/user/create")
    public String LogInController(HTTPServletRequest request, HTTPServletResponse response) {
        Map<String, String> query = request.getQuery();
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        Database.addUser(user);
        return HOME_PATH.getPath();
    }
    @RequestMapping(path = "/user/login")
    public String LogInTestController(HTTPServletRequest request, HTTPServletResponse response) {
        Map<String, String> query = request.getQuery();
        String userId = query.get("userId");
        User findUser = Database.findUserById(userId);
        if (findUser == null) {
            return FAILED_PATH.getPath();
        }
        SessionManager.createSession(findUser, response);
        return HOME_PATH.getPath();
    }


    private boolean isLogInStatus(HTTPServletRequest request) {
        return SessionManager.getSession(request) == null;
    }
}
