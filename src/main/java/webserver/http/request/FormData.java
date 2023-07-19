package webserver.http.request;

import webserver.util.Parser;

import java.util.Map;

public class FormData {
    private final Map<String, String> formDataMap;
    public FormData(String formData) {
        formDataMap = Parser.parseFormData(formData);
    }

    public Map<String, String> getFormDataMap() {
        return formDataMap;
    }
}
