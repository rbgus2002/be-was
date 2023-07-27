package webserver;

import annotation.RequestMapping;
import annotation.RendererMapping;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.wrapper.Queries;
import domain.Post;
import exception.UnauthorizedException;
import service.PostService;
import domain.User;
import service.UserService;
import view.renderer.*;

import java.util.List;
import java.util.Optional;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;

public class Controller {

    @RequestMapping(method = GET, path = "/")
    public ModelView root(HttpRequest request, HttpResponse response) {
        return new ModelView("Hello world");
    }

    @RendererMapping(name = IndexHtmlRenderer.class)
    @RequestMapping(method = GET, path = "/index.html")
    public ModelView index(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/index.html");
        addUserToModelIfLogin(mv, request);

        List<Post> posts = PostService.getAllPosts();
        mv.addModelAttribute("posts", posts);

        return mv;
    }

    @RendererMapping(name = HtmlRenderer.class)
    @RequestMapping(method = GET, path = "/user/form.html")
    public ModelView userForm(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/form.html");
        addUserToModelIfLogin(mv, request);
        return mv;
    }

    @RendererMapping(name = HtmlRenderer.class)
    @RequestMapping(method = GET, path = "/user/login.html")
    public ModelView userLoginPage(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/login.html");
        addUserToModelIfLogin(mv, request);
        return mv;
    }

    @RequestMapping(method = POST, path = "/user/create")
    public ModelView userCreate(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        UserService.createUser(
                queries.getValue("userId"),
                queries.getValue("password"),
                queries.getValue("name"),
                queries.getValue("email")
        );

        return new ModelView("redirect:/index.html");
    }

    @RequestMapping(method = POST, path = "/user/login")
    public ModelView userLogin(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        Optional<User> user = UserService.login(
                queries.getValue("userId"),
                queries.getValue("password")
        );

        // 로그인 실패
        if (user.isEmpty()) {
            return new ModelView("redirect:/user/login_failed.html");
        }

        // 세션 생성
        UserSessionManager.createSession(user.get(), response, "/");

        return new ModelView("redirect:/index.html");
    }

    @RendererMapping(name = HtmlRenderer.class)
    @RequestMapping(method = GET, path = "/user/login_failed.html")
    public ModelView userLoginFailed(HttpRequest request, HttpResponse response) {
        ModelView mv = new ModelView("/user/login_failed.html");
        addUserToModelIfLogin(mv, request);
        return mv;
    }

    @RendererMapping(name = UserListRenderer.class)
    @RequestMapping(method = GET, path = "/user/list.html")
    public ModelView listUser(HttpRequest request, HttpResponse response) {
        User loginUser = UserSessionManager.getSession(request);

        if (loginUser != null) {
            ModelView mv = new ModelView("/user/list.html");
            mv.addModelAttribute("user", loginUser);
            mv.addModelAttribute("users", UserService.getAllUsers());

            return mv;
        }

        return new ModelView("redirect:/index.html");
    }

    @RequestMapping(method = GET,  path = "/user/logout")
    public ModelView logout(HttpRequest request, HttpResponse response) {
        UserSessionManager.destroySession(request);
        return new ModelView("redirect:/index.html");
    }

    @RendererMapping(name = HtmlRenderer.class)
    @RequestMapping(method = GET, path = "/post/write.html")
    public ModelView postForm(HttpRequest request, HttpResponse response) {
        User loginUser = UserSessionManager.getSession(request);

        if (loginUser != null) {
            ModelView mv = new ModelView("/post/write.html");
            mv.addModelAttribute("user", loginUser);

            return mv;
        }

        return new ModelView("redirect:/user/login.html");
    }

    @RequestMapping(method = POST, path = "/post/create")
    public ModelView createPost(HttpRequest request, HttpResponse response) {
        User loginUser = validatedUser(request);

        Queries queries = request.getQueries();
        PostService.createPost(loginUser, queries.getValue("title"), queries.getValue("contents"));

        return new ModelView("redirect:/index.html");
    }

    @RendererMapping(name = ShowPostRenderer.class)
    @RequestMapping(method = GET, path = "/post/show.html")
    public ModelView showPost(HttpRequest request, HttpResponse response) {
        User loginUser = UserSessionManager.getSession(request);

        if (loginUser != null) {
            ModelView mv = new ModelView("/post/show.html");
            Queries queries = request.getQueries();

            Post post = PostService.findPost(queries.getValue("postId"));

            mv.addModelAttribute("user", loginUser);
            mv.addModelAttribute("post", post);
            mv.addModelAttribute("isOwner", loginUser == post.getUser());

            return mv;
        }

        return new ModelView("redirect:/user/login.html");
    }

    @RendererMapping(name = EditPostHtmlRenderer.class)
    @RequestMapping(method = GET, path = "/post/edit.html")
    public ModelView editPostForm(HttpRequest request, HttpResponse response) {
        User loginUser = validatedUser(request);

        Queries queries = request.getQueries();
        Post post = PostService.findPost(queries.getValue("postId"));

        if (isPostOwner(post, loginUser)) {
            ModelView mv = new ModelView("/post/edit.html");
            mv.addModelAttribute("post", post);

            return mv;
        }

        return new ModelView("redirect:/index.html");
    }

    @RequestMapping(method = POST, path = "/post/edit")
    public ModelView editPost(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();

        User loginUser = validatedUser(request);
        Post post = PostService.findPost(queries.getValue("postId"));

        if (isPostOwner(post, loginUser)) {
            PostService.editPost(post.getPostId(), queries.getValue("title"), queries.getValue("contents"));

            return new ModelView("redirect:/post/show.html?postId=" + post.getPostId());
        }

        return new ModelView("redirect:/index.html");
    }

    @RequestMapping(method = POST, path = "/post/delete")
    public ModelView deletePost(HttpRequest request, HttpResponse response) {
        Queries queries = request.getQueries();
        String postId = queries.getValue("postId");

        User loginUser = validatedUser(request);
        Post post = PostService.findPost(postId);

        if (isPostOwner(post, loginUser)) {
            PostService.deletePost(postId);
        }

        return new ModelView("redirect:/index.html");
    }

    private void addUserToModelIfLogin(ModelView mv, HttpRequest request) {
        User user = UserSessionManager.getSession(request);

        if (user != null) {
            mv.addModelAttribute("user", user);
        }
    }

    private User validatedUser(HttpRequest request) {
        User user = UserSessionManager.getSession(request);

        if (user == null) {
            throw new UnauthorizedException("인증되지 않은 사용자");
        }

        return user;
    }

    private boolean isPostOwner(Post post, User user) {
        return post.getUser() == user;
    }
}
