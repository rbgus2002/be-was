package controller;

import java.util.HashMap;
import java.util.Map;

public class FrontController {

	private static final Map<String, Object> instances = new HashMap<>();

	public FrontController() {
		initializeInstances();
	}

	private void initializeInstances() {
		instances.put(Controller.class.getName(), new Controller());
	}

	public Object getInstance(String className) {
		return instances.get(className);
	}
}
