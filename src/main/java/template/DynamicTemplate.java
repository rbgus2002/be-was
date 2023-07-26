package template;

import domain.user.User;

import java.util.Map;

public class DynamicTemplate {

    private final String dynamicTagFormat = "<dynamic id=\"%s\"></dynamic>";
    private final String userNameTag = "<li><a>%s 로그인 됨</a></li>\n";
    private final String loginTag = "<li><a href=\"/user/login.html\" role=\"button\">로그인</a></li>\n";
    private final String signUpTag = "<li><a href=\"/user/form.html\" role=\"button\">회원가입</a></li>\n";
    private final String logoutTag = "<li><a href=\"#\" role=\"button\">로그아웃</a></li>\n";
    private final String editPrivacyTag = "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>";

    public String decorateHeaderBar(String html, Map<String, Object> model) {
        User user = (User) model.get("user");
        boolean login = user != null;

        // 로그인 O
        if (login) {
            String userNameTag = String.format(this.userNameTag, user.getName());

            // 로그인 버튼 => 사용자 이름
            // 회원가입 버튼 X
            // 로그아웃 버튼 O
            // 개인정보 수정 버튼 O
            html = replaceTag(html, "login", userNameTag);
            html = replaceTag(html, "signUp", "");
            html = replaceTag(html, "logout", logoutTag);
            html = replaceTag(html, "privacy", editPrivacyTag);
        }

        // 로그인 X
        else {
            // 로그인 버튼 O
            // 회원가입 버튼 O
            // 로그아웃 버튼 X
            // 개인정보 수정 버튼 X
            html = replaceTag(html, "login", loginTag);
            html = replaceTag(html, "signUp", signUpTag);
            html = replaceTag(html, "logout", "");
            html = replaceTag(html, "privacy", "");

        }

        return html;
    }

    public String decorate(String html, Map<String, Object> model) {
        return html;
    }

    protected String replaceTag(String html, String dynamicTagId, String replaceTo) {
        String tag = String.format(dynamicTagFormat, dynamicTagId);
        return html.replace(tag, replaceTo);
    }

}
