package webserver.view.view;

import exception.internalServerError.FileRenderException;
import webserver.Constants.ContentType;
import webserver.Constants.HttpVersion;

import java.io.DataOutputStream;
import java.util.Map;

public interface View {
    void render(final HttpVersion version, final ContentType contentType, final Map<String, Object> model, final DataOutputStream dos) throws FileRenderException;
}
