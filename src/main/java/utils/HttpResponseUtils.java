package utils;

import common.ContentType;
import common.HttpResponse;
import common.ResponseCode;
import common.ResponseLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseUtils {

    private HttpResponseUtils() {
    }

    public static HttpResponse createDefaultHeaderResponse(
            String version,
            ResponseCode code,
            ContentType contentType,
            byte[] body
    ) {
        ResponseLine responseLine = new ResponseLine(version, code);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType.getDescription());
        headers.put("Content-Length", String.valueOf(body.length));

        return new HttpResponse(responseLine, headers, body);
    }

    public static void sendResponse(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBytes(response.getResponseLine());
        dos.writeBytes(response.getHeaders());
        dos.writeBytes("\r\n");
        dos.write(response.getBody());
        dos.flush();
    }
}
