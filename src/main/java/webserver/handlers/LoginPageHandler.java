package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.model.Model;
import webserver.session.Session;
import webserver.template.TemplateRenderer;
import webserver.utils.FileUtils;

public class LoginPageHandler implements Handler{
    TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        if (session.isValid()) {
            return HttpResponse.redirect("/index.html");
        }

        Model model = makeModel();
        String html = renderHtml(request, model);

        return HttpResponse.okWithFile(html.getBytes(), Mime.HTML);
    }

    private String renderHtml(HttpRequest request, Model model) {
        String html = getRequestHtml(request);
        html = templateRenderer.render(html, model);
        return html;
    }

    private static String getRequestHtml(HttpRequest request) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);
        return new String(file);
    }

    private static Model makeModel() {
        Model model = new Model();
        model.setAttribute("loginStatus", "false");
        return model;
    }
}
