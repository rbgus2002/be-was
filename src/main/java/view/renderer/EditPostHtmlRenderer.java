package view.renderer;

import domain.Post;

import java.util.Map;

public class EditPostHtmlRenderer extends HtmlRenderer {
    private final String EDIT_POST_FORMAT =
            "<div class=\"form-group\">\n" +
            "   <label for=\"title\">제목</label>\n" +
            "   %s" +
            "</div>\n" +
            "<div class=\"form-group\">\n" +
                "<label for=\"contents\">내용</label>\n" +
                "%s" +
            "</div>" +
            "<input type=\"hidden\" name=\"postId\" value=\"%s\"/>";

    private final String POST_TITLE_FORMAT = "<input type=\"text\" class=\"form-control\" id=\"title\" name=\"title\" value=\"%s\"/>\n";
    private final String POST_CONTENTS_FORMAT = "<textarea name=\"contents\" id=\"contents\" rows=\"5\" class=\"form-control\">%s</textarea>\n";

    @Override
    public String decorate(String html, Map<String, Object> model) {
        Post post = (Post) model.get("post");

        String postTitle = String.format(POST_TITLE_FORMAT, post.getTitle());
        String postContents = String.format(POST_CONTENTS_FORMAT, post.getContents());
        String postInfo = String.format(EDIT_POST_FORMAT, postTitle, postContents, post.getPostId());

        return replaceTag(html, "postInfo", postInfo);
    }
}
