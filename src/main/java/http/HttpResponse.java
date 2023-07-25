package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ContentType;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static http.HttpStatus.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final String PATH = "src/main/resources";
    private final String REDIRECT = "redirect:";
    private final String NOT_SUPPORT_ERROR_PAGE = "src/main/resources/templates/not_support_error.html";
    private final String NOT_FOUND_ERROR_PAGE = "src/main/resources/templates/not_found_error.html";
    private final String INDEX = "/index.html";
    private final String MAIN_PAGE = "src/main/resources/templates" + INDEX;

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
        } else if (status == FOUND) {
            response302Header(dos);
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

    public void setResults(String filePath, HttpStatus status) throws IOException {
        if (isRedirect(filePath)) {
            status = FOUND;
        }
        this.status = status;
        byte[] body = convertFilePathToBody(filePath);
        this.body = body;
    }

    private boolean isRedirect(String filePath) {
        return REDIRECT.equals(filePath);
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
        } else if (status == FOUND) {
            fullPath = MAIN_PAGE;
        }
        return fullPath;
    }
}
