package application.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webserver.Constants.HttpStatus;
import webserver.Constants.HttpVersion;
import webserver.request.HttpRequest;
import webserver.request.RequestQuery;
import webserver.response.HttpResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setup() {
        userController = new UserController();
    }

    @Test
    @DisplayName("유저가 정상적으로 생성되어야 한다.")
    void createUser() {
        HttpRequest mockRequest = Mockito.mock(HttpRequest.class);
        RequestQuery mockQuery = Mockito.mock(RequestQuery.class);

        when(mockRequest.getVersion()).thenReturn(HttpVersion.HTTP_1_1);
        when(mockRequest.getRequestQuery()).thenReturn(mockQuery);
        when(mockQuery.getValue("userId")).thenReturn("1");
        when(mockQuery.getValue("password")).thenReturn("password");
        when(mockQuery.getValue("name")).thenReturn("name");
        when(mockQuery.getValue("email")).thenReturn("email@example.com");

        HttpResponse response = userController.createUser(mockRequest);

        assertEquals(HttpResponse.ofWithStatusOnly(HttpVersion.HTTP_1_1, HttpStatus.CREATED), response);
    }
}