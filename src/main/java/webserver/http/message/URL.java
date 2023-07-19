package webserver.http.message;

import java.util.HashMap;
import java.util.Map;

public class URL {
	private String path;
	private Map<String, String> queryMap;
	private URL() {
	}

	public static URL of(String urlString) {
		URL url = new URL();
		String[] tokens = urlString.split("\\?");
		url.path = tokens[0];
		url.queryMap = parseQueryString(tokens[1]);
		return url;
	}

	private static Map<String, String> parseQueryString(String queryString) {
		Map<String, String> queryMap = new HashMap<>();
		String[] queryList = queryString.split("&");
		for (String query : queryList) {
			String[] queryTokens = query.split("=");
			queryMap.put(queryTokens[0], queryTokens[1]);
		}
		return queryMap;
	}

	public String getPath() {
		return path;
	}
}
