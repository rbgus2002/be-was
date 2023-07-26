package controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import view.IndexView;
import view.user.ListView;

public class FrontController {

	private static final Map<String, Object> instances = new HashMap<>();

	public FrontController() {
		initializeInstances();
	}

	private void initializeInstances() {
		instances.put(Controller.class.getName(), new Controller());
		instances.put(IndexView.class.getName(), new IndexView());
		instances.put(ListView.class.getName(), new ListView());
	}

	public Object getInstance(String className) {
		return instances.get(className);
	}

	public Method[] getDeclaredMethods() {
		return instances.values()
			.stream()
			.flatMap(clazz -> Arrays.stream(clazz.getClass().getDeclaredMethods()))
			.toArray(Method[]::new);
	}
}
