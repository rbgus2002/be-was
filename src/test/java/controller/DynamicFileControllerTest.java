package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.http.model.Request;
import webserver.http.model.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

class DynamicFileControllerTest {
    private final String testDirectory = "./src/test/java/controller/";
    private static final DynamicFileController dynamicFileController = new DynamicFileController();

    @BeforeEach
    public void setup() {
        UserService.clearUserDatabase();
    }

    @Test
    public void showUserList() throws Exception {
        // Given
        UserService.userSignup("jst0951", "password", "정성태", "jst0951@gmail.com");

        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = new Request(in);

        // When
        Response response = dynamicFileController.showUserList(request);

        // Then

    }
}
