
package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestUtils {
    private final InputStream in;
    private final InputStreamReader ir;
    private final BufferedReader br;
    public RequestUtils(InputStream in){
        this.in = in;
        this.ir = new InputStreamReader(in, StandardCharsets.UTF_8);
        this.br = new BufferedReader(ir);
    }

    public String parseRequestUrl() throws IOException {
        String line = br.readLine();

        String[] tokens = line.split(" ");

        return tokens[1];
    }
}