package webserver.http.response;

import exception.NotSupportedContentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ContentType;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static webserver.http.HttpStatus.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final String PATH = "src/main/resources";
    private final String NOT_SUPPORT_ERROR_PAGE = "src/main/resources/templates/not_support_error.html";
    private final String NOT_FOUND_ERROR_PAGE = "src/main/resources/templates/not_found_error.html";

    private byte[] body;
    private HttpStatus status;

    private HttpResponse() {
    }

    public static HttpResponse init() {
        return new HttpResponse();
    }

    public void writeResponseToOutputStream(OutputStream out, ContentType type) {
        DataOutputStream dos = new DataOutputStream(out);

        if (status == OK) {
            response200Header(dos, type);
        } else if (status == NOT_FOUND) {
            response400Header(dos, type);
        } else if (status == BAD_REQUEST) {
            response404Header(dos, type);
        }

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

    public void setResults(String filePath, HttpStatus status) throws IOException {
        this.status = status;
        byte[] body = convertFilePathToBody(filePath);
        this.body = body;
    }

    private byte[] convertFilePathToBody(String filePath) throws IOException {
        return Files.readAllBytes(new File(getFullPath(filePath)).toPath());
    }

    private String getFullPath(String filePath) {
        String fullPath = PATH + filePath;
        if (status == NOT_FOUND) {
            fullPath = NOT_FOUND_ERROR_PAGE;
        } else if (status == BAD_REQUEST) {
            fullPath = NOT_SUPPORT_ERROR_PAGE;
        }
        return fullPath;
    }
}
