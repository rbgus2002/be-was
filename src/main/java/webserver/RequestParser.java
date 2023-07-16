package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private String uri;
    private Mime mime;

    private void RequestParser() {
    }


    public static RequestParser createRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String startLine = br.readLine();
        logger.debug("startLine = {}", startLine);
        String line = "";
        while(!(line = br.readLine()).isEmpty()){
            logger.debug("request info = {}", line);
        }



        return new RequestParser();

    }

}
