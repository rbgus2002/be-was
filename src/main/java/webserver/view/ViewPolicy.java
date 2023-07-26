package webserver.view;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewPolicy {
	/**
	 * Annotation 메소드 정의로 HTML 뷰의 렌더링 정책을 지정할 수 있다.
	 * @param line body에서 regex에 해당하는 문장
	 * @param model Controller에서 attribute가 추가된 모델
	 * @return body에서 regex에 해당하는 부분의 대체 결과가 될 문장
	 */
	@annotations.ViewPolicy(regex = "<li only_non_user=\"true\">.*?<\\/li>")
	public String onlyShowForNonUser(String line, ModelView modelView) {
		if (modelView.containsAttribute("login")) {
			if (modelView.getAttribute("login").equals("true")) {
				return "";
			}
		}
		return line;
	}

	@annotations.ViewPolicy(regex = "<li only_login_user=\"true\">.*?<\\/li>")
	public String onlyShowForLoginUser(String line, ModelView modelView) {
		if (modelView.containsAttribute("login")) {
			if (modelView.getAttribute("login").equals("true")) {
				return line;
			}
		}
		return "";
	}

	@annotations.ViewPolicy(regex = "\\[\\[\\$\\{.*?\\}\\]\\]")
	public String replaceWithVariable(String line, ModelView modelView) {
		Pattern pattern = Pattern.compile("\\[\\[\\$\\{(.*?)\\}\\]\\]");
		Matcher matcher = pattern.matcher(line);
		String variableName = "";
		while (matcher.find()) {
			variableName = matcher.group(1);
		}

		if (modelView.getAttribute(variableName) == null) {
			return "";
		}
		return (String)modelView.getAttribute(variableName);
	}

	@annotations.ViewPolicy(regex = "<tr repeat=\".*\">(.|\\s)*?<\\/tr>")
	public String repeatForAttributes(String line, ModelView modelView) {
		Pattern pattern = Pattern.compile("repeat=\"(.*?)\"");
		Matcher matcher = pattern.matcher(line);
		String attributeName = "";
		while (matcher.find()) {
			attributeName = matcher.group(1);
		}
		List<Map<String, String>> attributes = (List<Map<String, String>>)modelView.getAttribute(attributeName);

		StringBuilder result = new StringBuilder();
		int currentIter = 1;
		for (Map<String, String> attribute : attributes) {
			String newLine = line.replaceAll("\\$\\{iter\\}", String.valueOf(currentIter));
			for (String value : attribute.keySet()) {
				newLine = newLine.replaceAll("\\$\\{" + value + "\\}", attribute.get(value));
			}
			result.append(newLine);
		}

		return result.toString();
	}
}
