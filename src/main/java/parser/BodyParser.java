package parser;

import model.ContentType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public final class BodyParser {

    private BodyParser() {}

    public static Object parseBody(String body, ContentType contentType) {
        if(contentType.equals(ContentType.APPLICATION_URLENCODED)) {
            String[] bodyParsed = body.split("&");

            Map<String, String> dataset = new LinkedHashMap<>();

            for(String data : bodyParsed) {
                StringTokenizer stringTokenizer = new StringTokenizer(data, "=");
                dataset.put(stringTokenizer.nextToken(), stringTokenizer.nextToken());
            }

            return dataset;
        }

        if(contentType.equals(ContentType.TEXT_PLAIN)) {

        }

        if(contentType.equals(ContentType.APPLICATION_JSON)) {

        }

        if(contentType.equals(ContentType.MULTIPART_FORMDATA)) {

        }

        throw new RuntimeException("지원되지 않는 Content Type입니다.");
    }
}
