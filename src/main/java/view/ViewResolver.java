package view;

import model.ContentType;
import model.HttpRequest;
import model.HttpStatus;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ViewResolver {

    /**
     * controller에 매핑되지 않는 URL 요청이나 static 파일 요청이 왔을 때 해당 URL 위치의 파일을 가리키는 View 객체 반환
     * 루트에 대해서는 controller가 없더라도 static에서 걸러내어 별도의 index.html로 가기위한 처리를 함
     * @param request
     * @return View
     */
    public View getStaticView(HttpRequest request) {
        View view = new View();

        String requestURI = request.getRequestURI();

        if(requestURI.endsWith(".html") || requestURI.equals("/")) {
            view.setView(requestURI);
            view.setStatus(HttpStatus.OK);
            view.setContentType(ContentType.TEXT_HTML);
            view.setStaticValue(false);
            return view;
        }

        view.setView(requestURI);
        view.setStatus(HttpStatus.OK);
        view.setStaticValue(true);

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
        view.setContentType(mv.getContentType());
        view.setStaticValue(false);

        view.setView(path);

        return view;
    }
}
