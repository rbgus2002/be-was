package webserver.http.response;

import static webserver.http.Http.CRLF;
import static webserver.http.Http.LINE_DELIMITER;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void response(final HttpResponse httpResponse, final OutputStream outputStream) throws IOException {
        logger.debug(httpResponse.getHeaders().toString());

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(httpResponse.getResponseLine().toString() + LINE_DELIMITER);
        dataOutputStream.writeBytes(httpResponse.getHeaders().toString() + LINE_DELIMITER);
        dataOutputStream.writeBytes(CRLF);
        dataOutputStream.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
        dataOutputStream.flush();
    }
}
