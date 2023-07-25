package common.wrapper;

import common.http.Cookie;

import javax.annotation.Nullable;

public class Headers extends KeyValuePair {

    /**
     * HTTP Request Message의 Cookie
     */
    @Nullable
    Cookies cookies;

    /**
     * HTTP Response Message의 Set-Cookie
     */
    @Nullable
    SetCookies setCookies;

    public Headers() {
        super();
    }

    public Headers(String headerString) {
        super(headerString, "\r\n", ":");
    }

    public Cookies getCookies() {
        if (cookies != null) {
            return cookies;
        }

        if (map.containsKey("Cookie")) {
            String cookieString = map.get("Cookie");
            cookies = new Cookies(cookieString);
            return cookies;
        }

        return new Cookies();
    }

    public SetCookies getSetCookies() {
        if (setCookies == null) {
            setCookies = new SetCookies();
        }

        return setCookies;
    }

    public void addSetCookies(Cookie cookie) {
        if (setCookies == null) {
            setCookies = new SetCookies();
        }

        setCookies.addCookie(cookie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Headers that = (Headers) o;

        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

}
