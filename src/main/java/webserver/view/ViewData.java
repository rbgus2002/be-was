package webserver.view;

import java.util.List;
import java.util.Map;

public class ViewData {
    private String path;
    private Map<String, String> matchedData;
    private List<List<String>> lineByLineData;

    private ViewData(String path, Map<String, String> matchedData, List<List<String>> lineByLineData) {
        this.path = path;
        this.matchedData = matchedData;
        this.lineByLineData = lineByLineData;
    }

    public static ViewData of(String path, Map<String, String> matchedData, List<List<String>> lineByLineData) {
        return new ViewData(path, matchedData, lineByLineData);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getMatchedData() {
        return matchedData;
    }

    public List<List<String>> getLineByLineData() {
        return lineByLineData;
    }
}
