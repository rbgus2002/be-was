package support.web.view;

import support.annotation.Container;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@Container
public class ErrorView implements View {

    @Override
    public String view(HttpRequest request, HttpResponse response) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<!DOCTYPE html>")
                .append("<html lang=\"en\">")
                .append("<head>")
                .append("    <meta charset=\"UTF-8\">")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("    <title>404 Not Found</title>")
                .append("    <style>")
                .append("        body {")
                .append("            font-family: Arial, sans-serif;")
                .append("            background-color: #f0f0f0;")
                .append("            margin: 0;")
                .append("            padding: 0;")
                .append("            display: flex;")
                .append("            justify-content: center;")
                .append("            align-items: center;")
                .append("            height: 100vh;")
                .append("        }")
                .append("")
                .append("        .container {")
                .append("            text-align: center;")
                .append("        }")
                .append("")
                .append("        h1 {")
                .append("            font-size: 5em;")
                .append("            color: #e74c3c;")
                .append("            margin: 0;")
                .append("        }")
                .append("")
                .append("        p {")
                .append("            font-size: 1.5em;")
                .append("            color: #444;")
                .append("        }")
                .append("")
                .append("        a {")
                .append("            color: #3498db;")
                .append("            text-decoration: none;")
                .append("            transition: color 0.2s ease-in-out;")
                .append("        }")
                .append("")
                .append("        a:hover {")
                .append("            color: #2980b9;")
                .append("        }")
                .append("    </style>")
                .append("</head>")
                .append("<body>")
                .append("    <div class=\"container\">")
                .append("        <h1>").append("에러").append("</h1>")
                .append("        <p>아이고! 이 페이지에는 너가 찾고 싶어하는 것이 없어!</p>")
                .append("        <p>다른 방식으로 접근하는 것을 권장하고 싶으니 돌아가, 요기 -><a href=\"/\">홈페이지</a>.</p>")
                .append("    </div>")
                .append("</body>")
                .append("</html>");

        return stringBuilder.toString();
    }
}
