package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.*;

public class Utils {
    private Utils() {
    }

    public static InputStream getResourceAsStream(URI requestUrl) {
        String[] split = requestUrl.toString().split("\\.");
        String ext = split[split.length - 1];
        return Utils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates/" : "/static/") + requestUrl);
    }

    public static Optional<HttpClient.Version> getHttpVersion(String version) {
        if (version.equals("HTTP/1.1")) {
            return Optional.of(HttpClient.Version.HTTP_1_1);
        } else if (version.equals("HTTP/2.0")) {
            return Optional.of(HttpClient.Version.HTTP_2);
        }
        return Optional.empty();
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
