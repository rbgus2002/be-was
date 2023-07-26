package domain.post;

import annotation.RequestMapping;
import annotation.TemplateMapping;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.wrapper.Queries;
import domain.user.User;
import template.DynamicTemplate;
import webserver.ModelView;
import webserver.UserSessionManager;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;

public class PostController {

    @TemplateMapping(name = DynamicTemplate.class)
    @RequestMapping(method = GET, path = "/post/write.html")
    public ModelView postForm(HttpRequest request, HttpResponse response) {
        User user = UserSessionManager.getSession(request);

        if (user != null) {
            ModelView mv = new ModelView("/post/form.html");
            mv.addModelAttribute("user", user);
            return mv;
        }

        return new ModelView("redirect:/index.html");
    }

    @RequestMapping(method = POST, path = "/post/create")
    public ModelView postCreate(HttpRequest request, HttpResponse response) {
        User user = UserSessionManager.getSession(request);

        if (user != null) {
            Queries queries = request.getQueries();
            PostService.createPost(user, queries.getValue("title"), queries.getValue("contents"));
        }

        return new ModelView("redirect:/index.html");
    }
}
