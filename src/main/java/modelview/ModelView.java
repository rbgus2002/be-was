package modelview;

import common.enums.ContentType;
import common.enums.ResponseCode;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private final String view;
    private final Map<String, Object> model;
    private ResponseCode responseCode;
    private final ContentType contentType;

    public ModelView(String view, ResponseCode responseCode) {
        this.view = view;
        this.model = new HashMap<>();
        this.responseCode = responseCode;

        if (ContentType.isHtmlContent(view)) {
            contentType = ContentType.HTML;
        }
        else {
            contentType = ContentType.PLAIN;
        }
    }

    public ModelView(String view) {
        this(view, ResponseCode.OK);
    }

    public String getViewName() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setAttribute(String key, Object value) {
        model.put(key, value);
    }

}
