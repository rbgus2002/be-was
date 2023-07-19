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
    private byte[] body;
    private HttpStatus status;

    private HttpResponse() {
    }

    public static HttpResponse init() {
        return new HttpResponse();
    }

    public void response(OutputStream out){
        DataOutputStream dos = new DataOutputStream(out);

        if(status == OK){
            response200Header(dos);
        }
//        else if(status == BAD_REQUEST){
//            response400Header(dos);
//        }else if(status == NOT_FOUND){
//            response404Header(dos);
//        }

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

    public void setResults(String filePath, HttpStatus status) throws IOException {
        byte[] body = convertFilePathToBody(filePath);
        this.body = body;
        this.status = status;
    }

    private byte[] convertFilePathToBody(String filePath) throws IOException {
        return Files.readAllBytes(new File(PATH + filePath).toPath());
    }
}
