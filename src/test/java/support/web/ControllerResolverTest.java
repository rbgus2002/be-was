package support.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

}