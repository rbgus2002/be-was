package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ClassListener {

    /**
     * 패키지내부에 작성된 모든 클래스의 이름을 가져온다.
     *
     * @param packageName 클래스를 검색할 package 이름을 나타냅니다. <br />
     *                    주로 "package.name.subname..." 형식으로 작성하는 것을 권장하며 '.' 대신 '/'를 작성하여도 무방합니다.
     */
    public static List<Class<?>> scanClass(String packageName) {
        String path = packageName.replace('.', '/');

        List<Class<?>> classes = new ArrayList<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader.getResource(path) != null) {
            scanClassesInDirectory(path, classes, classLoader);
        }

        return classes;
    }

    private static void scanClassesInDirectory(String path, List<Class<?>> classes, ClassLoader classLoader) {
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

        // 디렉토리일 경우 해당 디렉토리를 순회한다.
        if (file.exists() && file.isDirectory()) {
            File[] directory = file.listFiles();
            if (directory != null) {
                for (File fileInDirectory : directory) {
                    scanClassesInDirectory(("".equals(path) ? path : path + "/") + fileInDirectory.getName(), classes, classLoader);
                }
            }
            return;
        }

        // 파일 일 경우 클래스 경로에 추가한다.
        if (file.isFile() && file.getName().endsWith(".class")) {
            String className = path.substring(0, path.length() - 6).replace('/', '.');
            try {
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
