package webserver.http.controller;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import controller.HomeController;

public class ControllerMapper {

	private final Map<String, Controller> urlMapping = new HashMap<>();

	public ControllerMapper() {
		initUrlMapping();
	}

	private void initUrlMapping() {
		urlMapping.put("/index.html", new HomeController());
	}

	public Controller findController(String urlPath) {
		return urlMapping.get(urlPath);
	}

}
