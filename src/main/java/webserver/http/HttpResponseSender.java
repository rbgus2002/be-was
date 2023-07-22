package webserver.http;

import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HttpResponseSender {
    public static final String SPACE = " ";
    public static final String CRLF = "\r\n";


    public void sendResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(httpResponse.getHttpVersion().getVersion()).append(SPACE);
        HttpStatus httpStatus = httpResponse.getHttpStatus();
        responseBuilder.append(httpStatus.getStatusCode()).append(SPACE);
        responseBuilder.append(httpStatus.getReasonPhrase()).append(CRLF);
        dos.write(responseBuilder.toString().getBytes());
        dos.flush();

        Map<String, List<String>> metaData = httpResponse.getMetaData();
        for (String key : metaData.keySet()) {
            responseBuilder = new StringBuilder();
            String values = String.join(SPACE, metaData.get(key));
            responseBuilder.append(key).append(":").append(SPACE)
                    .append(values).append(CRLF);
            dos.write(responseBuilder.toString().getBytes());
            dos.flush();
        }

        dos.write(CRLF.getBytes());

        if (httpResponse.hasBody()) {
            dos.write(httpResponse.getBody());
            dos.flush();
        }
    }
}
