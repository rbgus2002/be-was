package controller;

import http.HttpUtil;
import model.Post;
import model.User;
import service.FileService;
import service.PostService;
import service.UserService;
import webserver.model.Request;
import webserver.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static http.HttpUtil.*;
import static http.HttpParser.*;
import static webserver.model.Request.Method;
import static service.FileService.readStaticFile;
import static service.SessionService.getUserIdBySid;

public class FileController {
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";
    private static final String NAVBAR_RIGHT = "<li>.*?/user/list\\.html.*?</li>";
    private static final String USERNAME_FORMAT = "<li>%s</li>";
    private static final String BUTTON_LOGOUT = "<li>.*?로그아웃.*?</li>";
    private static final String BUTTON_LOGIN = "<li>.*?로그인.*?</li>";
    private static final String BUTTON_SIGNUP = "<li>.*?회원가입.*?</li>";
    private static final String BUTTON_MODIFY_USERDATA = "<li>.*?개인정보수정.*?</li>";
    private static final String USER_LIST_TBODY = "<tbody>";
    private static final String USER_LIST_ROW_FORM = "<tr>\n<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>";
    private static final String POST_LIST_UL = "<ul class=\"list\">";
    private static final String POST_LIST_LI = "<li>\n" +
            "                  <div class=\"wrap\">\n" +
            "                      <div class=\"main\">\n" +
            "                          <strong class=\"subject\">\n" +
            "                              <a href=\"./qna/show.html\">%s</a>\n" +
            "                          </strong>\n" +
            "                          <div class=\"auth-info\">\n" +
            "                              <i class=\"icon-add-comment\"></i>\n" +
            "                              <span class=\"time\">2016-01-15 18:47</span>\n" +
            "                              <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
            "                          </div>\n" +
            "                          <div class=\"reply\" title=\"댓글\">\n" +
            "                              <i class=\"icon-reply\"></i>\n" +
            "                              <span class=\"point\">%s</span>\n" +
            "                          </div>\n" +
            "                      </div>\n" +
            "                  </div>\n" +
            "              </li>";

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
                httpDocument = removeElement(httpDocument, BUTTON_LOGOUT);
                httpDocument = removeElement(httpDocument, BUTTON_MODIFY_USERDATA);
            }
            else {
                httpDocument = appendElement(httpDocument, NAVBAR_RIGHT, String.format(USERNAME_FORMAT, getUserIdBySid(sid)));
                httpDocument = removeElement(httpDocument, BUTTON_LOGIN);
                httpDocument = removeElement(httpDocument, BUTTON_SIGNUP);
            }

            // 유저 목록(/user/list.html)
            if(targetUri.equals(USER_LIST_URL)) {
                if(sid == null) {
                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
                    return new Response(STATUS.SEE_OTHER, headerMap, null);
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for(User user: UserService.getAllUser()) {
                        i++;
                        String tr = String.format(USER_LIST_ROW_FORM, i, user.getUserId(), user.getName(), user.getEmail());
                        sb.append(tr);
                    }
                    httpDocument = appendElement(httpDocument, USER_LIST_TBODY, sb.toString());
                }
            }

            // 글 쓰기(/qna/form.html) 미로그인시
            if(targetUri.equals(QNA_FORM_URL) && request.getMethod() == Method.GET) {
                if(sid == null) {
                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put(HEADER_REDIRECT_LOCATION, USER_LOGIN_URL);
                    return new Response(STATUS.SEE_OTHER, headerMap, null);
                }
            }

            // 글 쓰기(/qna/form.html) 글 작성 완료 후 전송시
            if(targetUri.equals(QNA_FORM_URL) && request.getMethod() == Method.POST) {
                Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());
                PostService.addPost(bodyParameterMap);

                Map<String, String> headerMap = new HashMap<>();
                headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
                return new Response(STATUS.SEE_OTHER, headerMap, null);
            }

            // 글 목록(index.html)
            if(targetUri.equals(INDEX_URL) && request.getMethod() == Method.GET) {
                Collection<Post> postList = PostService.getAllPost();
                StringBuilder sb = new StringBuilder();
                for(Post post: postList) {
                    sb.append(String.format(POST_LIST_LI, post.getTitle(), post.getUserId(), post.getPostId()));
                }
                httpDocument = appendElement(httpDocument, POST_LIST_UL, sb.toString());
            }

            body = httpDocument.getBytes();
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    private static String removeElement(String source, String regex) {
        source = source.replaceAll(regex, "");

        return source;
    }

    private static String appendElement(String source, String regex, String tail) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        while(matcher.find()) {
            String foundString = matcher.group();
            source = source.replaceAll(regex, foundString + tail);
        }

        return source;
    }
}
