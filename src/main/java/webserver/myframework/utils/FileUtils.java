package webserver.myframework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class FileUtils {
    private FileUtils() {
    }

    public static String getExtension(File file) {
        String fileName = file.getName();

        int dotIndex = fileName.lastIndexOf('.');
        int slashIndex = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (dotIndex > slashIndex) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public static File getPackageDirectory(URL directoryURL) throws FileNotFoundException {
        File packageDirectory = new File(directoryURL.getFile());
        if (!packageDirectory.exists()) {
            throw new FileNotFoundException("디렉토리를 가져오는데 실패하였습니다");
        }
        return packageDirectory;
    }
}
