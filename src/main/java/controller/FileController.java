package controller;

import webserver.http.model.Request;
import webserver.http.model.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static webserver.http.HttpUtil.*;
import static service.FileService.readStaticFile;
import static service.SessionService.getUserIdBySid;

public abstract class FileController {
    protected static final String NAVBAR_RIGHT = "<li>.*?/user/list\\.html.*?</li>";
    protected static final String USERNAME_FORMAT = "<li>%s</li>";
    protected static final String BUTTON_LOGOUT = "<li>.*?로그아웃.*?</li>";
    protected static final String BUTTON_LOGIN = "<li>.*?로그인.*?</li>";
    protected static final String BUTTON_SIGNUP = "<li>.*?회원가입.*?</li>";
    protected static final String BUTTON_MODIFY_USERDATA = "<li>.*?개인정보수정.*?</li>";

    protected static String generateHttpDocument(Request request) {
        String targetUri = request.getTargetUri().split("\\?")[0];
        String targetPath = TEMPLATE_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            String sid = request.getSid();
            String httpDocument = new String(readStaticFile(TEMPLATE_FILEPATH + targetUri));

            // Navbar
            if(sid == null) {
                httpDocument = removeElement(httpDocument, BUTTON_LOGOUT);
                httpDocument = removeElement(httpDocument, BUTTON_MODIFY_USERDATA);
            }
            else {
                httpDocument = appendElement(httpDocument, NAVBAR_RIGHT, String.format(USERNAME_FORMAT, getUserIdBySid(sid)));
                httpDocument = removeElement(httpDocument, BUTTON_LOGIN);
                httpDocument = removeElement(httpDocument, BUTTON_SIGNUP);
            }

            return httpDocument;
        }

        return null;
    }

    protected static String removeElement(String source, String regex) {
        source = source.replaceAll(regex, "");

        return source;
    }

    protected static String appendElement(String source, String regex, String tail) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        while(matcher.find()) {
            String foundString = matcher.group();
            source = source.replaceAll(regex, foundString + tail);
        }

        return source;
    }

    protected static Response generate200Response(MIME mime, byte[] body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    protected static Response generate303Response(String redirectUrl) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, redirectUrl);
        return new Response(STATUS.SEE_OTHER, headerMap, null);
    }
}
