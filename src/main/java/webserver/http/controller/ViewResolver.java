package webserver.http.controller;

public class ViewResolver {

	private static final String STATIC_PATH = "src/main/resources/static/";
	private static final String TEMPLATES_PATH = "src/main/resources/templates/";
	private static final String HTML_EXTENSION = ".html";

	public String resolveViewName(String viewName) {
		return TEMPLATES_PATH + viewName + HTML_EXTENSION;
	}
}
