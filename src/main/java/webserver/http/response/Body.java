package webserver.http.response;

import webserver.http.MIME;

import java.io.DataOutputStream;
import java.io.IOException;

public class Body {
    private byte[] content;
    private MIME mime;
    private static final Body emptyBody = new Body(new byte[0], MIME.HTML);

    private Body(byte[] content, MIME mime) {
        this.content = content;
        this.mime = mime;
    }

    public static Body emptyBody() {
        return emptyBody;
    }

    public static Body from(byte[] body, MIME mime) {
        return new Body(body, mime);
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
