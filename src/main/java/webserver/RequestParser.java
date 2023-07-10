package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private String url;

    public RequestParser(InputStream in) throws IOException {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((input = br.readLine()) != null && !input.isEmpty()) {
            String[] tokens = input.split(" ");
            if (tokens[0].startsWith("GET")) {
                url = tokens[1];
            }
            logger.debug(input);
        }
    }

    public String getUrl() {
        return url;
    }
}
