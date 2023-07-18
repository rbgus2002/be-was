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

    public static String getExtension(String path) {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }
}
