package db;

import com.google.common.collect.Maps;
import model.Content;

import java.util.Map;
import java.util.Set;

public class ContentDatabase {
    private static Map<Integer, Content> contents = Maps.newHashMap();

    private static int index = 1;


    public static synchronized void addContent(Content content) {
       contents.put(index++, content);
    }

    public static Content findById(int id) {
        return contents.get(id);
    }

    public static Set<Map.Entry<Integer, Content>> findAllWithId() {
        return contents.entrySet();
    }
}
