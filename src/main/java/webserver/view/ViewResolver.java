package webserver.view;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import annotations.DeclaredViewPolicies;

public class ViewResolver {
	public static byte[] resolve(byte[] body, ModelView modelView) throws
		InvocationTargetException,
		IllegalAccessException {
		String stringBody = new String(body);
		for (String regex : DeclaredViewPolicies.getViewPoliciesRegex()) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(stringBody);
			while (matcher.find()) {
				stringBody = stringBody.replace(matcher.group(),
					DeclaredViewPolicies.runPolicyFor(regex, matcher.group(), modelView));
			}
		}
		return stringBody.getBytes();
	}
}
