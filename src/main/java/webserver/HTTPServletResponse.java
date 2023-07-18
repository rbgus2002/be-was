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

    private final String header;
    private final byte[] body;

    public HTTPServletResponse(byte[] body, String header) {
        this.body = body;
        this.header = header;
    }
}
