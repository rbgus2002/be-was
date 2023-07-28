package webserver.view.view;

import exception.internalServerError.FileRenderException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.util.Map;

public interface View {
    void render(final HttpRequest request, final HttpResponse response, final Map<String, Object> model, final DataOutputStream dos) throws FileRenderException;
}
