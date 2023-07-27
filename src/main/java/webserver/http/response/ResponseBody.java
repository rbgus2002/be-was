package webserver.http.response;

import webserver.http.MIME;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private byte[] content;
    private MIME mime;
    private static final ResponseBody emptyBody = new ResponseBody(new byte[0], MIME.HTML);

    private ResponseBody(byte[] content, MIME mime) {
        this.content = content;
        this.mime = mime;
    }

    public static ResponseBody emptyBody() {
        return emptyBody;
    }

    public static ResponseBody from(byte[] body, MIME mime) {
        return new ResponseBody(body, mime);
    }

    public int getLength() {
        return content.length;
    }

    public String getContentType() {
        return mime.getContentType();
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.write(content, 0, content.length);
    }

    @Override
    public String toString() {
        return new String(content);
    }
}
