package webserver;

import common.enums.ContentType;
import modelview.ModelView;
import view.HtmlView;
import view.RedirectView;
import view.TextView;
import view.View;

import static webserver.ServerConfig.TEMPLATE_PATH;

public class ViewResolver {
    private static final String REDIRECT_URL_PREFIX = "redirect:";

    public static View resolve(ModelView mv) {
        ContentType contentType = mv.getContentType();
        String viewName = mv.getViewName();

        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            viewName = viewName.substring(REDIRECT_URL_PREFIX.length()).trim();
            return new RedirectView(viewName);
        }

        if (contentType.isHtmlContent()) {
            return new HtmlView(TEMPLATE_PATH + viewName);
        }

        if (contentType.isPlainContent()) {
            return new TextView(viewName);
        }

        return new HtmlView(TEMPLATE_PATH + "/error.html");
    }

}
