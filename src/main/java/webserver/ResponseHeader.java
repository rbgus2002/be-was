package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ResponseHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final static String HTTP_VERSION = "HTTP/1.1";
    private final static String HTTP_200_OK = "200 OK";
    private final static String HTTP_302_FOUND = "302 Found";

    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes( HTTP_VERSION + " " + HTTP_200_OK + "\r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response302Header(DataOutputStream dos, String redirection) {
        try {
            dos.writeBytes(HTTP_VERSION + " " + HTTP_302_FOUND + "\r\n");
            dos.writeBytes("Location: " + redirection + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
