package webserver.http.response;

import webserver.http.MIME;

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

    public byte[] getContent() {
        return content;
    }
}
