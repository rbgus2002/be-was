package webserver.view;

public class ViewPolicy {
	@annotations.ViewPolicy(regex = "<li need_login=\"true\">.*<\\/li>")
	private String disableUserContent(String line, Model model) {
		if (model.containsAttribute("login")) {
			if (model.getAttribute("login").equals("true")) {
				return line;
			}
		}
		return "";
	}
}
