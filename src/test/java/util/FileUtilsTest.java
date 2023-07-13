package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileUtilsTest {

    @Test
    @DisplayName("bufferedReader를 list로 변환한다.")
    void convertBufferedReaderToList() throws IOException {
        //given
        String newLine = System.getProperty("line.separator");
        InputStream inputStream = new ByteArrayInputStream(("a" + newLine + "bb" + newLine + "ccc").getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //when
        List<String> strings = FileUtils.convertBufferedReaderToList(bufferedReader);

        //then
        List<String> expected = List.of("a", "bb", "ccc");
        assertThat(strings).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("경로에 파일이 존재하지 않으면 예외가 발생한다.")
    void fileNotExist() {
        //given
        String path = "/wrong.html";

        //when then
        assertThrows(FileNotFoundException.class, () -> FileUtils.getResourceAsStream(path));
    }
}
