package utils;

import common.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseUtils {

    private HttpResponseUtils() {}

    public static void sendResponse(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBytes(response.toStringResponseLine());
        dos.writeBytes(response.toStringHeaders());
        dos.writeBytes("\r\n");
        dos.write(response.getBody());
        dos.flush();
    }
}