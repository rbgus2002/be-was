package webserver;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {

    BufferedReader br;

    public HttpRequestParser(InputStream in){
        this.br = new BufferedReader(new InputStreamReader(in));

    }

    public String getHeader() throws IOException {
        StringBuilder headerBuilder = new StringBuilder(StringUtils.appendLineSeparator(""));
        String line = br.readLine();

        while (!line.equals("")){
            headerBuilder.append(StringUtils.appendLineSeparator(line));
            line = br.readLine();
        }

        return headerBuilder.toString();
    }
}
