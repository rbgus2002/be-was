package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private String url;
    private Map<String, String> params = new HashMap<>();

    public RequestParser(InputStream in) throws IOException {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((input = br.readLine()) != null && !input.isEmpty()) {
            String[] tokens = input.split(" ");
            if (tokens[0].startsWith("GET")) {
                parseParams(tokens[1]);
            }
            logger.debug(input);
        }
    }

    private void parseParams(String fullUrl) {
        if (fullUrl.contains("?")) {
            String[] split = fullUrl.split("\\?");
            url = split[0];
            String[] split1 = split[1].split("&");
            for (String s : split1) {
                String[] split2 = s.split("=");
                params.put(split2[0], split2[1]);
            }
        } else {
            url = fullUrl;
        }
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }
}
