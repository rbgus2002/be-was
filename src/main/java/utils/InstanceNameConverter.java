package utils;

public abstract class InstanceNameConverter {

    public static String convert(String name) {
        return name.replaceFirst(".*\\.", "");
    }

}
