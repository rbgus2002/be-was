package controller;

import annotations.MyGetMapping;
import exception.BadRequestException;
import global.constant.Headers;
import global.constant.StatusCode;
import global.response.ResponseEntity;

import java.io.IOException;

public class Controller {
    @MyGetMapping(path = "/")
    public String root() {
        return ResponseEntity
                .responseBody("Hello world!")
                .build();
    }

    @MyGetMapping(path = "/index.html")
    public String getIndexHtml() {
        try {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, "/index.html")
                    .responseResource("/index.html")
                    .build();
        } catch (IOException e) {
            throw new BadRequestException();
        }
    }

    @MyGetMapping(path = "/user/form.html")
    public String getFormHtml() {
        try {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, "/user/form.html")
                    .responseResource("/user/form.html")
                    .build();
        } catch (IOException e) {
            throw new BadRequestException();
        }
    }
}
