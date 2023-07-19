package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public interface HttpController {
    Logger logger = LoggerFactory.getLogger(HttpController.class);

    String process(HttpRequest request, HttpResponse response) throws IOException;
}
