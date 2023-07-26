package application.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import webserver.http.Constants.HttpVersion;
import webserver.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

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
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);
        RequestBody mockBody = Mockito.mock(RequestBody.class);

        when(mockRequest.getVersion()).thenReturn(HttpVersion.HTTP_1_1);
        when(mockRequest.getRequestBody()).thenReturn(mockBody);
        when(mockBody.getValue("userId")).thenReturn("1");
        when(mockBody.getValue("password")).thenReturn("password");
        when(mockBody.getValue("name")).thenReturn("name");
        when(mockBody.getValue("email")).thenReturn("email@example.com");

        ModelAndView modelAndView = userController.createUser(mockRequest, mockResponse);

        assertEquals("redirect:", modelAndView.getViewName());
        assertNull(modelAndView.getModel());
    }
}