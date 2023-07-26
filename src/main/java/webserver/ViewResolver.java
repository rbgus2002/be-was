package webserver;

import view.*;

import static webserver.ServerConfig.TEMPLATE_PATH;

public class ViewResolver {
    private static final String REDIRECT_URL_PREFIX = "redirect:";

    public static View resolveViewName(String viewName) {
        // 리다이렉션
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            viewName = viewName.substring(REDIRECT_URL_PREFIX.length()).trim();
            return new RedirectView(viewName);
        }

        TemplateMapper templateMapper = TemplateMapper.getInstance();

        // 동적 템플릿 적용이 필요한 HTML
        if (templateMapper.contains(viewName)) {
            return new DynamicHtmlView(TEMPLATE_PATH + viewName, templateMapper.getDynamicTemplate(viewName));
        }

        // 템플릿 적용이 필요하지 않은 HTML
        if (viewName.endsWith(".html")) {
            return new HtmlView(TEMPLATE_PATH + viewName);
        }

        // 단순 문자열
        return new PlainTextView(viewName);
    }

}
