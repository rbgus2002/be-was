package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class FileUtils {
    private FileUtils() {
    }

    public static InputStream getResourceAsStream(String path) {
        String[] split = path.split("\\.");
        String ext = split[split.length - 1];
        return FileUtils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates" : "/static") + path);
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
