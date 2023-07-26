package view;

import model.ContentType;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;

public class View {

    private String view;
    private ContentType contentType;
    private HttpStatus status;

    public View() {
        this.status = HttpStatus.NOT_FOUND;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getView() {
        return view;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Response에 렌더링한 view를 넣어줌. 이때 ModelAndView에 데이터가 존재하면 해당 데이터를 view에 삽입
     * @param request
     * @param response
     * @param view
     */
    public void render(HttpRequest request, HttpResponse response, ModelAndView view) {

    }
}
