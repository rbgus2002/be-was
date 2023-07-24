package view;

import common.enums.ContentType;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public interface View {

    ContentType getContentType();

    void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception;

}
