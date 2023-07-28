package support.web.view;

import support.web.Model;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface View {

    String getName();

    String render(HttpRequest request, HttpResponse response, Model model);

}
