package global.constant;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusCodeTest {

    SoftAssertions softAssertions;
    @BeforeEach
    void setup() {
        softAssertions = new SoftAssertions();
    }

    @Test
    @DisplayName("StatusCode의 상태 코드를 가져온다")
    public void testGetStatusCode() {
        //given
        String OK = "200";
        String BAD_REQUEST = "400";
        String INTERNAL_SERVER_ERROR = "500";

        //when&then
        softAssertions.assertThat(OK).as("200 코드를 검증한다.").isEqualTo(StatusCode.OK.getStatusCode());
        softAssertions.assertThat(BAD_REQUEST).as("400 코드를 검증한다.").isEqualTo(StatusCode.BAD_REQUEST.getStatusCode());
        softAssertions.assertThat(INTERNAL_SERVER_ERROR).as("500 코드를 검증한다.").isEqualTo(StatusCode.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    @DisplayName("StatusCode의 상태 메시지를 가져온다")
    public void testGetStatus() {
        //given
        String OK = "OK";
        String BAD_REQUEST = "Bad Request";
        String INTERNAL_SERVER_ERROR = "Internal Server Error";


        //when&then
        softAssertions.assertThat(OK).as("200 코드를 검증한다.").isEqualTo(StatusCode.OK.getStatus());
        softAssertions.assertThat(BAD_REQUEST).as("400 코드를 검증한다.").isEqualTo(StatusCode.BAD_REQUEST.getStatus());
        softAssertions.assertThat(INTERNAL_SERVER_ERROR).as("500 코드를 검증한다.").isEqualTo(StatusCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}
