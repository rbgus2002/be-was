package webserver.view.view;

import webserver.Constants.ContentType;
import webserver.Constants.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface View {
    void render(final HttpVersion version, final ContentType contentType, final Map<String, Object> model, final DataOutputStream dos) throws IOException;
}
