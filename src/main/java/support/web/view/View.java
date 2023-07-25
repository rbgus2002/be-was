package support.web.view;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface View {

    String getName();

    String view(HttpRequest request, HttpResponse response);

}
