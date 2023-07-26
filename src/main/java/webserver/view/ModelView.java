package webserver.view;

public class ModelView {
	private Model model = new Model();
	private String path;

	private ModelView(String path) {
		this.path = path;
	}

	public static ModelView from(String path) {
		return new ModelView(path);
	}

	public String getPath() {
		return path;
	}

	public ModelView setPath(final String path) {
		this.path = path;
		return this;
	}

	public void addAttribute(String key, Object value) {
		model.addAttribute(key, value);
	}

	public Object getAttribute(String key) {
		return model.getAttribute(key);
	}

	public boolean containsAttribute(String key) {
		return model.containsAttribute(key);
	}
}
