package support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControllerResolverTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreOutputStream() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("메세지 호출 테스트")
    void test() throws InvocationTargetException, IllegalAccessException {
        //given
        String url = "/user/create";

        //when
        ControllerResolver.invoke(url);
        String output = outputStream.toString().trim();

        //then
        System.setOut(originalOut);
        System.out.println(output);
        assertNotNull(output);
        assertNotEquals(0, output.length());
    }

}