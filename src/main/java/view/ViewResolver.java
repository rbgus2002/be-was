package view;

import model.ContentType;
import model.HttpRequest;
import model.HttpStatus;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ViewResolver {

    private final String STATIC_PATH = "src/main/resources/static";
    private final String DYNAMIC_PATH = "src/main/resources/templates";

    /**
     * controller에 매핑되지 않는 URL 요청이나 static 파일 요청이 왔을 때 해당 URL 위치의 파일을 가리키는 View 객체 반환
     * @param request
     * @return View
     */
    public View getStaticView(HttpRequest request) {
        View view = new View();

        String requestURI = request.getRequestURI();
        if(requestURI.endsWith(".html")) {
            view.setView(DYNAMIC_PATH + requestURI);
            view.setStatus(HttpStatus.OK);
            view.setContentType(ContentType.TEXT_HTML);
            return view;
        }

        view.setView(STATIC_PATH + requestURI);
        view.setStatus(HttpStatus.OK);

        if(requestURI.endsWith(".css")) {
            view.setContentType(ContentType.TEXT_CSS);
        }

        if(requestURI.endsWith(".js")) {
            view.setContentType(ContentType.TEXT_JAVASCRIPT);
        }

        if(requestURI.endsWith("favicon.ico")) {
            view.setContentType(ContentType.IMAGE_X_ICON);
        }

        if(requestURI.endsWith(".png")) {
            view.setContentType(ContentType.IMAGE_PNG);
        }

        if(requestURI.endsWith(".ttf")) {
            view.setContentType(ContentType.FONT_TTF);
        }

        if(requestURI.endsWith(".woff")) {
            view.setContentType(ContentType.FONT_WOFF);
        }

        return view;
    }

    public View getDynamicView(ModelAndView mv) {
        View view = new View();

        String path = mv.getView();

        view.setStatus(mv.getStatus());
        view.setContentType(view.getContentType());

        if(!Files.exists(Paths.get(path + ".html"))) {
            view.setView(DYNAMIC_PATH + path + ".html");
        }

        return view;
    }
}
