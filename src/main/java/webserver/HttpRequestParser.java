package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private static final HttpRequestParser requestParser = new HttpRequestParser();
    private HttpRequestParser() {

    }

    public static HttpRequestParser getInstance() {
        return requestParser;
    }

    public HttpRequest getRequest(InputStream in) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return parseRequest(br);
    }

    private HttpRequest parseRequest(BufferedReader br) throws IOException {
        if(!br.ready()){
            return new HttpRequest();
        }
        String line = br.readLine();
        String[] firstLine = line.split(" ");
        return new HttpRequest(firstLine[0], firstLine[1].split("[?]")[0], initParams(line), initHeader(br, line), initBody(br));

    }

    private Map<String, String> initParams(String line) {
        String[] params = line.split("[?]");
        Map<String, String> paramsMap = new HashMap<>();
        if(params.length > 1){
            Arrays.stream(params[1].split("[&]"))
                    .filter(param -> param.split("[=]").length == 2)
                    .forEach(param -> paramsMap.put(param.split("[=]")[0],param.split("[=]")[1]));
        }

        return paramsMap;
    }

    private String initHeader(BufferedReader br, String line) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        while (!line.equals("")) {
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }
        return headerBuilder.toString();
    }

    private String initBody(BufferedReader br) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        while (br.ready()){
            bodyBuilder.append((char)br.read());
        }

        return bodyBuilder.toString();
    }

}
