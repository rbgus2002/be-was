package controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.http.model.Request;
import webserver.http.model.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpUtil.*;

class DynamicFileControllerTest {
    private final String testDirectory = "./src/test/java/controller/";
    private static final DynamicFileController dynamicFileController = new DynamicFileController();

    @BeforeEach
    public void setup() {
        UserService.clearUserDatabase();
    }

    @Test
    public void showUserListRedirect() throws Exception {
        // Given
        UserService.userSignup("jst0951", "q1w2e3r4", "정성태", "jst0951@gmail.com");

        InputStream in = new FileInputStream(testDirectory + "userListGet.txt");
        Request request = new Request(in);

        // When
        Response response = dynamicFileController.showUserList(request);

        // Then
        assertThat(response.getStatus()).isEqualTo(STATUS.SEE_OTHER);
    }
}
