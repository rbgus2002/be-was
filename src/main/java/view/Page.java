package view;

public class Page {

    public String getErrorPage(String errorMessage) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Error Page</title>" +
                "</head>" +
                "<body>" +
                "<h1>Error: " + errorMessage + "</h1>" +
                "</body>" +
                "</html>";
    }
}
