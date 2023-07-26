package webserver.view;

public class ViewPolicy {
	/**
	 * Annotation 메소드 정의로 HTML 뷰의 렌더링 정책을 지정할 수 있다.
	 * @param line body에서 regex에 해당하는 문장
	 * @param model Controller에서 attribute가 추가된 모델
	 * @return body에서 regex에 해당하는 부분의 대체 결과가 될 문장
	 */
	@annotations.ViewPolicy(regex = "<li only_non_user=\"true\">.*<\\/li>")
	public String disableUserConntent(String line, ModelView modelView) {
		if (modelView.containsAttribute("login")) {
			if (modelView.getAttribute("login").equals("true")) {
				return "";
			}
		}
		return line;
	}
}
