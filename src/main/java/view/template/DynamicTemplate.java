package view.template;

import domain.User;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DynamicTemplate {

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ");
    private final String DYNAMIC_TAG_FORMAT = "<dynamic id=\"%s\"></dynamic>";
    private final String USER_NAME_TAG_FORMAT = "<li><a>%s 로그인 됨</a></li>\n";
    private final String LOGIN_TAG = "<li><a href=\"/user/login.html\" role=\"button\">로그인</a></li>\n";
    private final String SIGNUP_TAG = "<li><a href=\"/user/form.html\" role=\"button\">회원가입</a></li>\n";
    private final String LOGOUT_TAG = "<li><a href=\"#\" role=\"button\">로그아웃</a></li>\n";
    private final String EDIT_PRIVACY_TAG = "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>";

    public String decorateHeaderBar(String html, Map<String, Object> model) {
        User user = (User) model.get("user");
        boolean login = user != null;

        // 로그인 O
        if (login) {
            String userNameTag = String.format(USER_NAME_TAG_FORMAT, user.getName());

            // 로그인 버튼 => 사용자 이름
            // 회원가입 버튼 X
            // 로그아웃 버튼 O
            // 개인정보 수정 버튼 O
            html = replaceTag(html, "login", userNameTag);
            html = replaceTag(html, "signUp", "");
            html = replaceTag(html, "logout", LOGOUT_TAG);
            html = replaceTag(html, "privacy", EDIT_PRIVACY_TAG);
        }

        // 로그인 X
        else {
            // 로그인 버튼 O
            // 회원가입 버튼 O
            // 로그아웃 버튼 X
            // 개인정보 수정 버튼 X
            html = replaceTag(html, "login", LOGIN_TAG);
            html = replaceTag(html, "signUp", SIGNUP_TAG);
            html = replaceTag(html, "logout", "");
            html = replaceTag(html, "privacy", "");

        }

        return html;
    }

    public String decorate(String html, Map<String, Object> model) {
        return html;
    }

    protected String replaceTag(String html, String dynamicTagId, String replaceTo) {
        String tag = String.format(DYNAMIC_TAG_FORMAT, dynamicTagId);
        return html.replace(tag, replaceTo);
    }

}
