package common.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class KeyValuePair {

    protected final Map<String, String> map = new HashMap<>();

    protected KeyValuePair() {}

    protected KeyValuePair(String source, String pairDelimiter, String keyValueDelimiter) {
        String[] pairs = source.trim().split(pairDelimiter);

        for (String pair : pairs) {
            String[] keyValue = extractKeyValue(pair, keyValueDelimiter);
            map.put(keyValue[0], keyValue[1]);
        }
    }

    public void addAttribute(String key, String value) {
        map.put(key, value);
    }

    public Set<String> getKeys() {
        return map.keySet();
    }

    public String getValue(String key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    private String[] extractKeyValue(String pair, String keyValueDelimiter) {
        int indexKeyValueDelimiter = pair.indexOf(keyValueDelimiter);

        if (indexKeyValueDelimiter == -1) {
            throw new RuntimeException();
        }

        return new String[]{
                pair.substring(0, indexKeyValueDelimiter).trim(),
                pair.substring(indexKeyValueDelimiter + 1).trim()
        };
    }

}
