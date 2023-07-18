package webserver.http.request;

import webserver.util.HeaderParser;

import java.util.Map;

public class FormData {
    private final Map<String, String> formDataMap;
    public FormData(String formData) {
        formDataMap = HeaderParser.parseFormData(formData);
    }

    public Map<String, String> getFormDataMap() {
        return formDataMap;
    }
}
