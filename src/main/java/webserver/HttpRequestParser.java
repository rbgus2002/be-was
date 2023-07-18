package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {
    private String method;
    private String url;
    private String header;
    private String body;
    private String line;

    public HttpRequestParser(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        parseRequest(br);
    }

    private void parseRequest(BufferedReader br) throws IOException {
        line = br.readLine();
        if(br.ready()){
            initMethodAndUrl();
            initHeader(br);
            initBody(br);
        }
    }

    private void initMethodAndUrl() {
        String[] firstLine = line.split(" ");
        this.method = firstLine[0];
        this.url = firstLine[1];
    }

    private void initHeader(BufferedReader br) throws IOException {
        this.header = getLines(br);
    }

    private void initBody(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (br.ready()){
            stringBuilder.append((char)br.read());
        }

        this.body = stringBuilder.toString();
    }

    private String getLines(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (!line.equals("")) {
            stringBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }

        return stringBuilder.toString();
    }

    public String getHeader() {
        return this.header;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.url;
    }

    public String getBody() {
        return this.body;
    }

}
