package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Path;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static util.Path.*;
import static webserver.Mime.*;

public class HTTPServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletResponse.class);

    private String header;
    private byte[] body;

    private final DataOutputStream dos;

    public HTTPServletResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    public DataOutputStream getWriter(){
        return dos;
    }
}
