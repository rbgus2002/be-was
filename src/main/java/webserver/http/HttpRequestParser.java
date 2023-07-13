package webserver.http;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestParser {
    HttpRequest parse(InputStream inputStream) throws IOException;
}
