package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static webserver.http.HttpStatus.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpStatus status;
    private byte[] body;

    public HttpResponse(HttpStatus status, byte[] body) {
        this.status = status;
        this.body = body;
    }

    public void response(OutputStream out){
        DataOutputStream dos = new DataOutputStream(out);

        if(status == OK){
            response200Header(dos, body.length);
        }else if(status == BAD_REQUEST){
            response400Header(dos, body.length);
        }else if(status == NOT_FOUND){
            response404Header(dos, body.length);
        }

        responseBody(dos, body);
    }

    private void response404Header(DataOutputStream dos, int length) {
    }

    private void response400Header(DataOutputStream dos, int lengthOfBodyContent) {
    
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
