package webserver.handlers;

import model.User;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.model.Model;
import webserver.session.Session;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

public class IndexHandler implements Handler {
    private final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        String path = request.getURL().getPath();

        Model model = makeModel(session);

        String html = renderHtml(path, model);

        return HttpResponse.okWithFile(html.getBytes(), Mime.HTML);
    }

    private String renderHtml(String path, Model model) {
        byte[] file = FileUtils.readFileFromTemplate(path);
        return templateRenderer.render(new String(file), model);
    }

    private static Model makeModel(Session session) {
        Model model = new Model();
        model.setAttribute("loginStatus", "false");
        if (session.isValid()) {
            model.setAttribute("loginStatus", "true");
            User user = session.getUser();
            model.setAttribute("userName", user.getName());
        }
        return model;
    }
}
