package http;

import exception.NotSupportedContentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ContentType;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static http.HttpStatus.*;
import static webserver.ContentType.NONE;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String ROOT_PATH = "src/main/resources";
    private static final String NOT_SUPPORT_ERROR_PAGE = "src/main/resources/templates/not_support_error.html";
    private static final String NOT_FOUND_ERROR_PAGE = "src/main/resources/templates/not_found_error.html";
    private static final String INDEX = "/index.html";
    private static final String MAIN_PAGE = "src/main/resources/templates" + INDEX;

    private byte[] body;

    private HttpStatus status;
    private String filePath;

    private HttpResponse(HttpStatus status, String filePath) {
        this.status = status;
        this.filePath = filePath;
    }

    public static HttpResponse redirect() {
        return new HttpResponse(FOUND, INDEX);
    }

    public static HttpResponse init(String filePath) {
        return new HttpResponse(OK, filePath);
    }

    public static HttpResponse ok(String filePath) {
        return new HttpResponse(OK, filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void mapResourcePath(ContentType type) {
        if (type == NONE) {
            throw new NotSupportedContentTypeException();
        }

        this.filePath = ROOT_PATH + type.getPath() + this.filePath;
    }

    public void doResponse() throws IOException {
        logger.debug("doResponse() START");
        byte[] body = convertFilePathToBody();
        this.body = body;
    }

    public void doResponse(HttpStatus status) throws IOException {
        logger.debug("doResponse(HttpStatus status) START");
        this.status = status;
        byte[] body = convertFilePathToBody();
        this.body = body;
    }

    private byte[] convertFilePathToBody() throws IOException {
        logger.debug("convertFilePathToBody >> now HttpResponse : {}", this);
        logger.debug("handlePathByHttpStatus 전 filePath : {}", this.filePath);
        handlePathByHttpStatus();
        logger.debug("handlePathByHttpStatus 후 filePath : {}", this.filePath);
        return Files.readAllBytes(new File(filePath).toPath());
    }

    private void handlePathByHttpStatus() {
        if (status == NOT_FOUND) {
            filePath = NOT_FOUND_ERROR_PAGE;
        } else if (status == BAD_REQUEST) {
            filePath = NOT_SUPPORT_ERROR_PAGE;
        } else if (status == FOUND) {
            filePath = MAIN_PAGE;
        }
    }

    public void writeResponseToOutputStream(OutputStream out) {
        logger.debug("writeResponseToOutputStream START");
        logger.debug("this.response : {}", this);
        DataOutputStream dos = new DataOutputStream(out);
        ContentType type = ContentType.findBy(this.filePath);
        if (status == OK) {
            response200Header(dos, type);
        } else if (status == NOT_FOUND) {
            response400Header(dos, type);
        } else if (status == BAD_REQUEST) {
            response404Header(dos, type);
        } else if (status == FOUND) {
            response302Header(dos);
        }
        logger.debug("status : {}", this.status);
        responseBody(dos);
    }

    private void response200Header(DataOutputStream dos, ContentType type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", type.getMime()));
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Location: " + INDEX + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response400Header(DataOutputStream dos, ContentType type) {
        try {
            dos.writeBytes("HTTP/1.1 400 NOT_FOUND \r\n");
            dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", type.getMime()));
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos, ContentType type) {
        try {
            dos.writeBytes("HTTP/1.1 404 BAD_REQUEST \r\n");
            dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", type.getMime()));
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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "status=" + status +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
