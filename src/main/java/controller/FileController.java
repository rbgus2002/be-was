package controller;

import http.HttpUtil;
import model.User;
import service.FileService;
import service.UserService;
import webserver.model.Request;
import webserver.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static http.HttpUtil.*;
import static service.FileService.readStaticFile;
import static service.SessionService.getUserIdBySid;

public class FileController {
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";
    private static final String NAVBAR_RIGHT = "/user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>";
    private static final String USERNAME_FORMAT = "<li>%s</li>";
    private static final String BUTTON_LOGOUT = "<li><a href=\"#\" role=\"button\">로그아웃</a></li>";
    private static final String BUTTON_LOGIN = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
    private static final String BUTTON_SIGNUP = "<li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>";
    private static final String BUTTON_MODIFY_USERDATA = "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>";
    private static final String USER_LIST_TBODY = "<tbody>";
    private static final String USER_LIST_ROW_FORM = "<tr>\n<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>";

    public static Response genereateResponse(Request request) throws IOException {
        String targetUri = request.getTargetUri();

        // Static Files
        String[] tokens = targetUri.split("\\.");
        String extension = tokens[tokens.length-1];
        HttpUtil.MIME mime = HttpUtil.MIME.getMimeByExtension(extension);
        if(mime == null) {
            return null;
        }
        String targetPath;
        byte[] body = null;

        targetPath = STATIC_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            body = readStaticFile(targetPath);
        }
        targetPath = TEMPLATE_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            String sid = request.getSid();
            String httpDocument = new String(readStaticFile(TEMPLATE_FILEPATH + targetUri));

            // Navbar
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

            // userList
            if(targetUri.equals("/user/list.html")) {
                if(sid != null) {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for(User user: UserService.getAllUser()) {
                        i++;
                        String tr = String.format(USER_LIST_ROW_FORM, i, user.getUserId(), user.getName(), user.getEmail());
                        sb.append(tr);
                    }
                    httpDocument = httpDocument.replace(USER_LIST_TBODY, USER_LIST_TBODY + sb);
                }
            }
            body = httpDocument.getBytes();
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }
}
