package webserver.request;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private HttpRequestParser() {

    }

    public static HttpRequest getRequest(InputStream in) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return parseRequest(br);
    }

    private static HttpRequest parseRequest(BufferedReader br) throws IOException {
        if(!br.ready()){
            return new HttpRequest();
        }
        String line = br.readLine();
        String[] firstLine = line.split(" ");
        return new HttpRequest(firstLine[0], firstLine[1].split("[?]")[0], initParams(firstLine[1]), initHeader(br, line), initBody(br));

    }

    private static Map<String, String> initParams(String line) {
        String[] params = line.split("[?]");
        Map<String, String> paramsMap = new HashMap<>();
        if(params.length > 1){
            Arrays.stream(params[1].split("[&]"))
                    .filter(param -> param.split("[=]").length == 2)
                    .forEach(param -> paramsMap.put(param.split("[=]")[0],param.split("[=]")[1]));
        }

        return paramsMap;
    }

    private static String initHeader(BufferedReader br, String line) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        while (line != null && !line.equals("")) {
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }
        return headerBuilder.toString();
    }

    private static String initBody(BufferedReader br) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        while (br.ready()){
            bodyBuilder.append((char)br.read());
        }

        return bodyBuilder.toString();
    }

}
