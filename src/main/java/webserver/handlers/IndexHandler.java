package webserver.handlers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.message.Mime;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.model.Model;
import webserver.template.TemplateRenderer;
import webserver.utils.ExtensionSeparator;
import webserver.utils.FileUtils;

public class IndexHandler implements Handler {
    private final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request, Session session, Model model) {
        String path = request.getURL().getPath();

        model.setAttribute("loginStatus", "false");
        if (session.isValid()) {
            model.setAttribute("loginStatus", "true");
            User user = session.getUser();
            model.setAttribute("userName", user.getName());
        }

        byte[] file = FileUtils.readFileFromTemplate(path);
        String ext = ExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        String html = templateRenderer.render(new String(file), model);

        return HttpResponse.okWithFile(html.getBytes(), mime);
    }
}
