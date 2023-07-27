package controller;

import model.Session;
import router.RequestMapping;
import service.SessionService;
import service.UserService;
import webserver.http.model.Request;
import webserver.http.model.Request.Method;
import webserver.http.model.Response;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpUtil.*;
import static webserver.http.HttpParser.*;
import static model.User.PASSWORD;
import static model.User.USERID;

public class ServiceController extends Controller {

    @RequestMapping(value="/user/create", method=Method.POST)
    public Response userCreate(Request request) {
        Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());

        UserService.userSignUp(bodyParameterMap);

        return generate303Response(INDEX_URL);
    }

    @RequestMapping(value="/user/login", method=Method.POST)
    public Response userLogin(Request request) {
        Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());
        String userId = bodyParameterMap.get(USERID);
        String password = bodyParameterMap.get(PASSWORD);

        // ID/PW 검증
        if(!UserService.validateUser(userId, password)) {
            return generate303Response("/user/login_failed.html");
        }

        // Session ID 추가
        Session session = SessionService.getSessionByUserId(userId);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
        headerMap.put(HEADER_SET_COOKIE, HEADER_SESSION_ID + session.getSessionId() + HEADER_COOKIE_PATH);

        return new Response(STATUS.SEE_OTHER, headerMap, null);
    }

    @RequestMapping(value="/user/logout", method=Method.GET)
    public Response userLogout(Request request) {
        String sid = request.getSid();
        if(SessionService.isSessionValid(sid)) {
            SessionService.deleteSession(sid);
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
        headerMap.put(HEADER_SET_COOKIE, HEADER_SESSION_ID + " " + "; max-age = 0");
        return new Response(STATUS.SEE_OTHER, headerMap, null);
    }
}
