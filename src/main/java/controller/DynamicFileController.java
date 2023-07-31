package controller;

import model.Post;
import model.User;
import router.RequestMapping;
import service.PostService;
import service.SessionService;
import service.UserService;
import webserver.http.model.Request;
import webserver.http.model.Response;

import java.util.Collection;
import java.util.Map;

import static service.PostService.*;
import static webserver.http.HttpUtil.*;
import static webserver.http.HttpParser.*;
import static webserver.http.model.Request.Method;

public class DynamicFileController extends FileController{
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
    private static final String QNA_AUTHOR_LABEL = "<label for=\"writer\">글쓴이</label>";
    private static final String QNA_AUTHOR_FORM = "<input class=\"form-control\" id=\"writer\" name=\"writer\" value=\"%s\" readonly placeholder=\"글쓴이\"/>";

    @RequestMapping(value="/user/list.html", method=Method.GET)
    public Response showUserList(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());
        String sid = request.getSid();

        if(sid == null) {
            return generate303Response(INDEX_URL);
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

        return generate200Response(mime, httpDocument.getBytes());
    }

    @RequestMapping(value="/qna/form.html", method=Method.GET)
    public Response qnaForm(Request request) {
        String httpDocument = generateHttpDocument(request);
        MIME mime = parseMime(request.getTargetUri());
        String sid = request.getSid();
        String userId = SessionService.getUserIdBySid(sid);

        if(sid == null) {
            return generate303Response("/user/login.html");
        }

        httpDocument = appendElement(httpDocument, QNA_AUTHOR_LABEL, String.format(QNA_AUTHOR_FORM, userId));

        return generate200Response(mime, httpDocument.getBytes());
    }

    @RequestMapping(value="/qna/form.html", method=Method.POST)
    public Response createQna(Request request) {
        PostService.addPost(
                request.getParameter(POST_WRITER),
                request.getParameter(POST_TITLE),
                request.getParameter(POST_CONTENT)
        );

        return generate303Response(INDEX_URL);
    }

    @RequestMapping(value="/qna/show.html?", method=Method.GET)
    public Response showQna(Request request) {
        String httpDocument = generateHttpDocument(request);
        String sid = request.getSid();
        MIME mime = parseMime(request.getTargetUri());
        Map<String, String> queryParameterMap = request.getQueryParameterMap();
        int postId = Integer.parseInt(queryParameterMap.get(Post.POST_ID));

        if(sid == null) {
            return generate303Response("/user/login.html");
        }

        Post post = PostService.getPostByPostId(postId);
        httpDocument = appendElement(httpDocument, QNA_TITLE, post.getTitle());
        httpDocument = appendElement(httpDocument, QNA_BODY, post.getContent());
        httpDocument = appendElement(httpDocument, QNA_AUTHOR, post.getUserId());

        return generate200Response(mime, httpDocument.getBytes());
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

        return generate200Response(mime, httpDocument.getBytes());
    }
}
