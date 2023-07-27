package webserver.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewData {
    private String path;
    private Map<String, String> matchedData;
    private Map<String, List<List<String>>> lineByLineData;

    private ViewData(String path, Map<String, String> matchedData, Map<String, List<List<String>>> lineByLineData) {
        this.path = path;
        this.matchedData = matchedData;
        this.lineByLineData = lineByLineData;
    }

    public static ViewData of(String path, Map<String, String> matchedData, Map<String, List<List<String>>> lineByLineData) {
        return new ViewData(path, matchedData, lineByLineData);
    }

    public static ViewData of(String path, Map<String, List<List<String>>> lineByLineData) {
        return new ViewData(path, new HashMap<>(), lineByLineData);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getMatchedData() {
        return matchedData;
    }

    public Map<String, List<List<String>>> getBlockData() {
        return lineByLineData;
    }
}
