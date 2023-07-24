package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    @DisplayName("map 자료구조인 헤더를 stringify 한다")
    void stringifyHeader() {
        Map<String, String> header = new HashMap<>();
        String key = "any";
        String value = "아무거나";
        header.put(key, value);
        header.put("asdf","asdf");
        header.put("123","abc");

        String result = StringUtils.mapToHeaderFormat(header);

        assertTrue(result.contains(key));
        assertTrue(result.contains(value));
    }
}