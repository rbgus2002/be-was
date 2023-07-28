package support.instance;

public class InstanceInfo {

    private final String name;
    private final Class<?> type;

    public InstanceInfo(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

}
