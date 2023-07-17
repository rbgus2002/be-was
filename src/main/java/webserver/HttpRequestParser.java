package webserver;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {

    BufferedReader br;
    String method;
    String url;
    String header;
    String body;

    public HttpRequestParser(InputStream in) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(in));
        parseRequest(br);
    }

    private void parseRequest(BufferedReader br) throws IOException {
        String line = br.readLine();

        initMethodAndUrl(line);
        initHeader(line, br);
    }

    private void initHeader(String line, BufferedReader br) throws IOException {
        StringBuilder headerBuilder = new StringBuilder(StringUtils.appendLineSeparator(""));

        while (line != null && !line.equals("")) {
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }

        header = headerBuilder.toString();
    }

    private void initMethodAndUrl(String line) {
        String[] firstLines = line.split(" ");
        method = firstLines[0];
        url = firstLines[1];
    }

    public String getHeader() throws IOException {
        return this.header;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.url;
    }

}
