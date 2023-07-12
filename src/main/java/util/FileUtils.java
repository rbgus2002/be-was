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

    public static <T> T createUserFromQueryParameters(Class<T> clazz, Map<String, String> queryParameters) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T object = constructor.newInstance();

            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue();

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
            }

            return object;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("객체를 생성하지 못했습니다.");
        }
    }
}
