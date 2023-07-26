package webserver.utils;

import webserver.http.HttpParameters;

public final class HttpParametersParser {
    private HttpParametersParser() {
    }

    public static HttpParameters parse(String parameters) {
        HttpParameters httpParameters = new HttpParameters();

        for (String parameter : parameters.split("&")) {
            addParameter(httpParameters, parameter);
        }

        return httpParameters;
    }

    private static void addParameter(HttpParameters httpParameters, String parameter) {
        String[] tokens = parameter.split("=");
        if (tokens.length == 2) {
            httpParameters.put(tokens[0], tokens[1]);
        }
    }
}
