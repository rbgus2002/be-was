package parser;

import webserver.HTTPServletRequest;

import java.io.BufferedReader;

public interface Parser {
    public HTTPServletRequest getProperRequest(BufferedReader br);


}
