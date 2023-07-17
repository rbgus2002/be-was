package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private DataOutputStream dos;
    private byte[] body;

    public HttpResponse(DataOutputStream dos, byte[] body) {
        this.dos = dos;
        this.body = body;
    }

    private void response200Header() throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody() throws IOException {
        dos.write(body, 0, body.length);
    }

    public void send() throws IOException {
        response200Header();
        responseBody();
        dos.flush();
    }
}
