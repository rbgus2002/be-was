package support.web.view;

import support.web.Model;
import utils.StringBuilderExpansion;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ErrorView implements View {

    @Override
    public String getName() {
        return "/error";
    }

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
                .append("        <h1>", "Error", "</h1>")
                .appendCRLF("        <p>Oops! This page doesn't have what you're looking for!</p>")
                .appendCRLF("        <p>I'd recommend a different approach. <a href=\"/\">homepage</a>.</p>")
                .appendCRLF("    </div>")
                .appendCRLF("</body>")
                .appendCRLF("</html>");

        return stringBuilder.toString();
    }
}
