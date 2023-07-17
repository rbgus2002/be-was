package parser;

import webserver.HTTPServletRequest;

import java.io.BufferedReader;

public class GetParser implements Parser{
    @Override
    public HTTPServletRequest getProperRequest(BufferedReader br) {
        return null;
    }

    private String findUrl(String startLine){
        return startLine.split(" ")[1];
    }
}
