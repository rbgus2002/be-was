package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    private FileUtils() {
    }

    public static InputStream getResourceAsStream(String path) throws FileNotFoundException {
        String[] split = path.split("\\.");
        String ext = split[split.length - 1];
        InputStream fileInputStream = FileUtils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates" : "/static") + path);
        if (fileInputStream == null) {
            throw new FileNotFoundException(path + "에 파일이 존재하지 않습니다.");
        }
        return fileInputStream;
    }

    public static List<String> convertBufferedReaderToList(BufferedReader reader) throws IOException {
        String line;
        List<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            strings.add(line);
        }
        return strings;
    }
}
