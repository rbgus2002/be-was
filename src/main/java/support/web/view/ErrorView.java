package support.web.view;

import support.annotation.Container;
import support.web.Model;
import utils.StringBuilderExpansion;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@Container
public class ErrorView implements View {

    @Override
    public String render(HttpRequest request, HttpResponse response, Model model) {
        StringBuilderExpansion stringBuilder = new StringBuilderExpansion();

        stringBuilder
                .appendCRLF("<!DOCTYPE html>")
                .appendCRLF("<html lang=\"en\">")
                .appendCRLF("<head>")
                .appendCRLF("    <meta charset=\"UTF-8\">")
                .appendCRLF("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .appendCRLF("    <title>404 Not Found</title>")
                .appendCRLF("    <style>")
                .appendCRLF("        body {")
                .appendCRLF("            font-family: Arial, sans-serif;")
                .appendCRLF("            background-color: #f0f0f0;")
                .appendCRLF("            margin: 0;")
                .appendCRLF("            padding: 0;")
                .appendCRLF("            display: flex;")
                .appendCRLF("            justify-content: center;")
                .appendCRLF("            align-items: center;")
                .appendCRLF("            height: 100vh;")
                .appendCRLF("        }")
                .appendCRLF("")
                .appendCRLF("        .container {")
                .appendCRLF("            text-align: center;")
                .appendCRLF("        }")
                .appendCRLF("")
                .appendCRLF("        h1 {")
                .appendCRLF("            font-size: 5em;")
                .appendCRLF("            color: #e74c3c;")
                .appendCRLF("            margin: 0;")
                .appendCRLF("        }")
                .appendCRLF("")
                .appendCRLF("        p {")
                .appendCRLF("            font-size: 1.5em;")
                .appendCRLF("            color: #444;")
                .appendCRLF("        }")
                .appendCRLF("")
                .appendCRLF("        a {")
                .appendCRLF("            color: #3498db;")
                .appendCRLF("            text-decoration: none;")
                .appendCRLF("            transition: color 0.2s ease-in-out;")
                .appendCRLF("        }")
                .appendCRLF("")
                .appendCRLF("        a:hover {")
                .appendCRLF("            color: #2980b9;")
                .appendCRLF("        }")
                .appendCRLF("    </style>")
                .appendCRLF("</head>")
                .appendCRLF("<body>")
                .appendCRLF("    <div class=\"container\">")
                .append("        <h1>", "에러", "</h1>")
                .appendCRLF("        <p>아이고! 이 페이지에는 너가 찾고 싶어하는 것이 없어!</p>")
                .appendCRLF("        <p>다른 방식으로 접근하는 것을 권장하고 싶으니 돌아가, 요기 -><a href=\"/\">홈페이지</a>.</p>")
                .appendCRLF("    </div>")
                .appendCRLF("</body>")
                .appendCRLF("</html>");

        return stringBuilder.toString();
    }
}
