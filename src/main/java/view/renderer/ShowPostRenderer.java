package view.renderer;

import domain.Post;

import java.util.Map;

public class ShowPostRenderer extends HtmlRenderer {

    private final String POST_INFO_FORMAT =
            "<header class=\"qna-header\">\n" +
            "   <h2 class=\"qna-title\">%s</h2>" +
            "</header>\n" +
            "<div class=\"content-main\">\n" +
            "   <article class=\"article\">\n" +
            "       <div class=\"article-header\">\n" +
            "           <a class=\"article-author-name\">작성자 : %s</a>" +
            "           <a class=\"article-header-time\">%s</a>" +
            "       </div>\n" +
            "       %s" +
            "       %s" +
            "   </article>\n" +
            "</div>";
    private  final String BUTTONS_FORMAT =
            "<br>\n"+
            "<div class=\"col-md-3 qna-write\">\n" +
            "   <form action=\"/post/edit.html\" method=\"get\">\n" +
            "       <input type=\"hidden\" name=\"postId\" value=\"%s\"><br>\n" +
            "       <button class=\"btn btn-primary pull-left\" type=\"submit\" style=\"margin-right: 40px\">수정하기</button>\n" +
            "   </form>"+
            "   <form action=\"/post/delete\" method=\"post\">\n" +
            "       <input type=\"hidden\" name=\"postId\" value=\"%s\">\n" +
            "       <button class=\"btn btn-primary pull-right btn-danger\" type=\"submit\">삭제하기</button>\n" +
            "   </form>"+
            "</div>\n";

    @Override
    public String decorate(String html, Map<String, Object> model) {
        Post post = (Post) model.get("post");
        boolean isOwner = (boolean) model.get("isOwner");

        if (post != null) {
            String buttons = isOwner ? String.format(BUTTONS_FORMAT, post.getPostId(), post.getPostId()) : "";
            String postInfo = String.format(
                    POST_INFO_FORMAT,
                    post.getTitle(),
                    post.getUser().getName(),
                    post.getDateTime().format(DATE_TIME_FORMATTER),
                    post.getContents(),
                    buttons
            );

            html = replaceTag(html, "postInfo", postInfo);
        }

        return html;
    }
}
