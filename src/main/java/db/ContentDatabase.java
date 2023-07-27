package db;

import com.google.common.collect.Maps;
import model.Content;
import model.User;

import java.util.Collection;
import java.util.Map;

public class ContentDatabase {
    private static Map<String, Content> contents = Maps.newHashMap();

    public static void addContent(Content content) {
        contents.put(content.getWriter(), content);
    }

    public static Content findUserById(String writer) {
        return contents.get(writer);
    }

    public static Collection<Content> findAll() {
        return contents.values();
    }
}
