package webserver.utils;

import webserver.http.HttpParameters;

public final class HttpParametersParser {
    private HttpParametersParser() {
    }

    public static HttpParameters parse(String parameters) {
        HttpParameters httpParameters = new HttpParameters();

        if (parameters == null || parameters.isBlank()) {
            return httpParameters;
        }

        for (String parameter : parameters.split("&")) {
            if (parameter.isEmpty()) {
                continue;
            }

            String[] tokens = parameter.split("=");
            if (tokens.length == 2) {
                httpParameters.put(tokens[0], tokens[1]);
            }
        }
        return httpParameters;
    }
}
