package support.web.view;

import support.web.Model;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface View {

    String render(HttpRequest request, HttpResponse response, Model model);

}
