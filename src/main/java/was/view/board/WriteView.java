package was.view.board;

import was.controller.annotation.RequestMapping;
import was.webserver.annotation.View;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.HttpSession;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpStatus;

@View
public class WriteView {

	@RequestMapping(method = HttpMethod.GET, path = "/write")
	public void write(HttpWasRequest request, HttpWasResponse response) {
		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionId = request.getSessionId();

		if (!httpSession.verifySession(sessionId)) {
			response.setHttpStatus(HttpStatus.FOUND);
			response.addHeader(HttpHeader.LOCATION, "/user/login.html");
			return;
		}

		response.responseResource("/write.html");
	}

}
