package controller;

import http.HttpUtil;
import model.Post;
import model.User;
import router.RequestMapping;
import service.PostService;
import service.UserService;
import webserver.model.Request;
import webserver.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static http.HttpUtil.*;
import static http.HttpParser.*;
import static webserver.model.Request.Method;
import static service.FileService.readStaticFile;
import static service.SessionService.getUserIdBySid;

public class DynamicFileController {
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
            "                              <a href=\"./qna/show.html?postId=%d\">%s</a>\n" +
            "                          </strong>\n" +
            "                          <div class=\"auth-info\">\n" +
            "                              <i class=\"icon-add-comment\"></i>\n" +
            "                              <span class=\"time\">2016-01-15 18:47</span>\n" +
            "                              <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
            "                          </div>\n" +
            "                          <div class=\"reply\" title=\"댓글\">\n" +
            "                              <i class=\"icon-reply\"></i>\n" +
            "                              <span class=\"postId\">%d</span>\n" +
            "                          </div>\n" +
            "                      </div>\n" +
            "                  </div>\n" +
            "              </li>";
    private static final String QNA_TITLE = "<h2 class=\"qna-title\">";
    private static final String QNA_BODY = "<div class=\"article-doc\">";
    private static final String QNA_AUTHOR = "<a class=\"article-author-name\">";

    @RequestMapping(value="/user/list.html", method=Method.GET)
    public Response showUserList(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());
        String sid = request.getSid();

        if(sid == null) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
            return new Response(STATUS.SEE_OTHER, headerMap, null);
        }
        else {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (User user : UserService.getAllUser()) {
                i++;
                String tr = String.format(USER_LIST_ROW_FORM, i, user.getUserId(), user.getName(), user.getEmail());
                sb.append(tr);
            }
            httpDocument = appendElement(httpDocument, USER_LIST_TBODY, sb.toString());
        }

        byte[] body = httpDocument.getBytes();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    @RequestMapping(value="/qna/form.html", method=Method.GET)
    public Response qnaForm(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());
        String sid = request.getSid();

        if(sid == null) {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_REDIRECT_LOCATION, "/user/login.html");
            return new Response(STATUS.SEE_OTHER, headerMap, null);
        }

        byte[] body = httpDocument.getBytes();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    @RequestMapping(value="/qna/form.html", method=Method.POST)
    public Response createQna(Request request) {
        Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());
        PostService.addPost(bodyParameterMap);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
        return new Response(STATUS.SEE_OTHER, headerMap, null);
    }

    @RequestMapping(value="/qna/show.html?", method=Method.GET)
    public Response showQna(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());
        Map<String, String> queryParameterMap = request.getQueryParameterMap();
        int postId = Integer.parseInt(queryParameterMap.get(Post.POST_ID));

        Post post = PostService.getPostByPostId(postId);
        httpDocument = appendElement(httpDocument, QNA_TITLE, post.getTitle());
        httpDocument = appendElement(httpDocument, QNA_BODY, post.getContent());
        httpDocument = appendElement(httpDocument, QNA_AUTHOR, post.getUserId());

        byte[] body = httpDocument.getBytes();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    @RequestMapping(value=INDEX_URL, method=Method.GET)
    public Response listQna(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());

        Collection<Post> postList = PostService.getAllPost();
        StringBuilder sb = new StringBuilder();
        for(Post post: postList) {
            sb.append(String.format(POST_LIST_LI, post.getPostId(), post.getTitle(), post.getUserId(), post.getPostId()));
        }
        httpDocument = appendElement(httpDocument, POST_LIST_UL, sb.toString());

        byte[] body = httpDocument.getBytes();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    private static String generateHttpDocument(Request request) {
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
