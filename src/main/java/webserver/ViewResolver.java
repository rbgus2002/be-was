package webserver;

import common.enums.ContentType;
import common.enums.ResponseCode;
import modelview.ModelView;
import view.HtmlView;
import view.TextView;
import view.View;

import static webserver.ServerConfig.TEMPLATE_PATH;

public class ViewResolver {

    public static View resolve(ModelView mv) {
        ContentType contentType = mv.getContentType();
        ResponseCode responseCode = mv.getResponseCode();

        if (contentType.isHtmlContent()) {
            return new HtmlView(TEMPLATE_PATH + mv.getViewName());
        }

        if (contentType.isPlainContent()) {
            return new TextView(mv.getViewName());
        }

        return new HtmlView(TEMPLATE_PATH + "/error.html");
    }

}
