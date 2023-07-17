package utils;

import exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static exception.BadRequestException.BAD_REQUEST_MESSAGE;

public class RequestParser {

    public static String parseUri(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String requestLine = br.readLine();

        if (requestLine == null) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }

        String[] tokens = requestLine.split(" ");
        return tokens[1];
    }
}
