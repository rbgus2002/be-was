package view.template;

import domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexTemplate extends DynamicTemplate {
    private final String POST_TITLE_TAG_FORMAT = "<strong class=\"subject\"><a href=\"/post/show.html?postId=%s\">%s</a></strong>";
    private final String POST_TIME_TAG_FORMAT = "<span class=\"time\">%s</span>";
    private final String POST_AUTHOR_TAG_FORMAT = "<a href=\"/user/profile.html\" class=\"author\">%s</a>";


    @Override
    public String decorate(String html, Map<String, Object> model) {
        StringBuilder listBuilder = new StringBuilder();

        List<Post> posts = getPostList(model);

        for (Post post : posts) {
            listBuilder.append("<li><div class=\"wrap\"><div class=\"main\">");
            listBuilder.append(String.format(POST_TITLE_TAG_FORMAT, post.getPostId(), post.getTitle()));
            listBuilder.append("<div class=\"auth-info\"><i class=\"icon-add-comment\"></i>");
            listBuilder.append(String.format(POST_TIME_TAG_FORMAT, post.getDateTime().format(DATE_TIME_FORMATTER)));
            listBuilder.append(String.format(POST_AUTHOR_TAG_FORMAT, post.getUser().getName()));
            listBuilder.append("</div>");
            listBuilder.append("</div></div></li>");
        }

        return replaceTag(html, "posts", listBuilder.toString());
    }

    private List<Post> getPostList(Map<String, Object> model) {
        List<Post> posts = new ArrayList<>();

        List<?> temps = (List<?>) model.get("posts");
        if (temps != null) {
            for (Object temp : temps) {
                posts.add((Post) temp);
            }
        }

        return posts;
    }

}
