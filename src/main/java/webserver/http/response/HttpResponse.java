package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static webserver.http.HttpStatus.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final String PATH = "src/main/resources/templates";

    private HttpStatus status;
    private final String filePath;
    private final byte[] body;

    private HttpResponse(HttpStatus status, String filePath) throws IOException {
        this.status = status;
        this.filePath = filePath;
        this.body = Files.readAllBytes(new File(PATH + filePath).toPath());
    }

    public static HttpResponse redirect() throws IOException {
        return new HttpResponse(OK, "/index.html");
    }

    public static HttpResponse findStatic(String resource) throws IOException {
        return new HttpResponse(OK, resource);
    }

    public void response(OutputStream out){
        DataOutputStream dos = new DataOutputStream(out);

        if(status == OK){
            response200Header(dos);
        }else if(status == BAD_REQUEST){
            response400Header(dos);
        }else if(status == NOT_FOUND){
            response404Header(dos);
        }

        responseBody(dos);
    }

    private void response404Header(DataOutputStream dos) {
    }

    private void response400Header(DataOutputStream dos) {
    
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
