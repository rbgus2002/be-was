package controllers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.statusline.StatusCode;
import webserver.view.ModelView;

public class ErrorHandler {
	public static ModelView buildErrorPage(final HttpRequest httpRequest, HttpResponse httpResponse,
		ModelView modelView) {
		httpResponse.setStatus(StatusCode.NOT_FOUND);
		modelView.addAttribute("statusCode", StatusCode.NOT_FOUND.code);
		modelView.addAttribute("statusMessage", StatusCode.NOT_FOUND.message);
		modelView.addAttribute("errorMessage", "페이지를 찾을 수 없습니다.");
		return modelView.setPath("error.html");
	}
}
