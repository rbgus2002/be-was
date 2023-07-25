package webserver.view;

import java.lang.reflect.InvocationTargetException;

import annotations.DeclaredViewPolicies;
import webserver.http.HttpResponse;

public class ViewResolver {
	public static String resolve(String body, Model model) throws InvocationTargetException, IllegalAccessException {
		for (String regex : DeclaredViewPolicies.getViewPoliciesRegex()) {
			body.replaceAll(regex, DeclaredViewPolicies.runPolicyFor(regex, body, model));
		}
		return body;
	}

	public static String resolve(HttpResponse httpResponse, Model model) throws InvocationTargetException, IllegalAccessException {
		for (String regex : DeclaredViewPolicies.getViewPoliciesRegex()) {
			body.replace(regex, DeclaredViewPolicies.runPolicyFor(regex, body, model));
		}
		return body;
	}

}
