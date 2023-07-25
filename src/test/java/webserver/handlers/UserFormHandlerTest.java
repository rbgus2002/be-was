package webserver.handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.fixture.HttpRequestFixture;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.model.Model;
import webserver.session.Session;
import webserver.utils.FileUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UserFormHandlerTest {
    @DisplayName("UserFormHandler는 요청이 들어오면 form.html을 반환한다")
    @Test
    void handleTest() {
        HttpRequest requestUserForm = HttpRequestFixture.getRequestUserForm();
        Session session = new Session("1");
        Model model = new Model();

        Handler handler = new UserFormHandler();
        HttpResponse response = handler.handle(requestUserForm, session, model);

        assertThat(response.getBody())
                .isEqualTo(FileUtils.readFileFromTemplate("/user/form.html"));
    }
}