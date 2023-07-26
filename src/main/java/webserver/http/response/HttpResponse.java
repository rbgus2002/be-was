package webserver.http.response;


import Service.UserService;
import model.User;
import webserver.http.response.view.View;

public class HttpResponse {

    private final ResponseMessageHeader responseMessageHeader;

    private String toUrl;
    private View view;

    private String cookie;

    public HttpResponse() {
        this.responseMessageHeader = new ResponseMessageHeader();
        view = null;
        cookie = null;
    }

    public HttpResponse(String cookie) {

        this.responseMessageHeader = new ResponseMessageHeader();
        view = null;
        this.cookie = cookie;
    }

    public ResponseMessageHeader getHeader() {
        return responseMessageHeader;
    }

    public void setToUrl(String url) {
        toUrl = url;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setBody(String toUrl) {
        view = new View(toUrl,getLoginUser());
    }

    private User getLoginUser() {
        UserService userService = UserService.of();
        if(cookie == null || !cookie.contains("sid=")) {
            return null;
        }
        String sId = cookie.split("sid=")[1].split(";")[0];
        return userService.getUser(sId);
    }

    public View getBody() {
        return view;
    }


    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}
