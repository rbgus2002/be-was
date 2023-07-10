
package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestHeader {
    List<String> headers = new ArrayList<>();
    public RequestHeader() throws IOException {
    }

    public void appendHeader(String header) {
        this.headers.add(header);
    }

    public String parseRequestUrl() throws IOException {
        String line =  headers.get(0);
        String[] tokens = line.split(" ");
        return tokens[1];
    }
}