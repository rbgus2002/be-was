package webserver.reponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    @DisplayName("file 형식에에 맞는 Content-Type을 반환해야 한다.")
    void getTypeTest (){
        verifyGetType("css", Type.CSS);
        verifyGetType("txt", Type.TEXT);
        verifyGetType("html", Type.HTML);
        verifyGetType("xml", Type.XML);
        verifyGetType("js", Type.JS);
        verifyGetType("ttf", Type.TTF);
        verifyGetType("png", Type.PNG);
        verifyGetType("eot", Type.EOT);
    }

    void verifyGetType(String contentType, Type type) {
        assertEquals(Type.getType(contentType), type);
    }

}