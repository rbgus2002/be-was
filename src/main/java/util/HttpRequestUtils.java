package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static String getRequestHeader(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                break;
            }

            stringBuilder.append(line).append("\n");
            logger.debug("request line: {}", line);
        }

        return stringBuilder.toString();
    }

    public static String getMethod(String header) {
        String[] tokens = header.split(" ");
        String method = tokens[0];
        logger.debug("request method: {}", method);

        return method;
    }

    public static String getPath(String header) {
        String[] tokens = header.split(" ");
        String path = tokens[1];
        logger.debug("request path: {}", path);

        return path;
    }

    public static boolean isValidPath(String path) {
        String[] splitPath = path.split("\\.");
        return splitPath[0].startsWith("/") && splitPath[1].equals("html");
    }
}
