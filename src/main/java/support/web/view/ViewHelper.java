package support.web.view;

import model.Session;
import utils.StringBuilderExpansion;

public class ViewHelper {

    public static StringBuilderExpansion dynamicLoginMenu(Session loginSession, StringBuilderExpansion stringBuilder) {
        if (loginSession != null) {
            stringBuilder.appendCRLF("                <li><a>", loginSession.getUser().getName(), "</a></li>")
                    .appendCRLF("                <li><a href=\"user/logout\" role=\"button\">로그아웃</a></li>");
        }
        if (loginSession == null) {
            stringBuilder.appendCRLF("                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>")
                    .appendCRLF("                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>");
        }

        return stringBuilder;
    }

}
