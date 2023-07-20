package service;

import webserver.model.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static http.HttpUtil.*;
import static service.SessionService.getUserIdBySid;

public class FileService {
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";
    private static final String NAVBAR_RIGHT = "<li><a href=\"./user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>";
    private static final String USERNAME_FORMAT = "<li>%s</li>";
    private static final String BUTTON_LOGOUT = "<li><a href=\"#\" role=\"button\">로그아웃</a></li>";
    private static final String BUTTON_LOGIN = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
    private static final String BUTTON_SIGNUP = "<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>";
    private static final String BUTTON_MODIFY_USERDATA = "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>";

    public static byte[] loadFile(Request request) throws IOException {
        String targetUri = request.getTargetUri();
        String targetPath;
        byte[] body = null;

        targetPath = STATIC_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            body = readStaticFile(targetPath);
        }
        targetPath = TEMPLATE_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            String sid = request.getSid();
            body = renderTemplateFile(targetUri, sid);
        }

        return body;
    }

    private static byte[] readStaticFile(String route) throws IOException {
        return Files.readAllBytes(new File(route).toPath());
    }
    private static byte[] renderTemplateFile(String targetUri, String sid) throws IOException {
        String httpDocument = new String(readStaticFile(TEMPLATE_FILEPATH + targetUri));
        if(sid == null) {
            httpDocument = httpDocument.replace(BUTTON_LOGOUT, "");
            httpDocument = httpDocument.replace(BUTTON_MODIFY_USERDATA, "");
        }
        else {
            httpDocument = httpDocument.replace(NAVBAR_RIGHT, NAVBAR_RIGHT +
                    String.format(USERNAME_FORMAT, getUserIdBySid(sid)));
            httpDocument = httpDocument.replace(BUTTON_LOGIN, "");
            httpDocument = httpDocument.replace(BUTTON_SIGNUP, "");
        }
        return httpDocument.getBytes();
    }
}
