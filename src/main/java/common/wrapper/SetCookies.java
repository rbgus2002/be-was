package common.wrapper;

import common.http.Cookie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetCookies implements Iterable<Cookie> {

    List<Cookie> responseCookies;

    public SetCookies() {
        this.responseCookies = new ArrayList<>();
    }

    public void addCookie(Cookie cookie) {
        responseCookies.add(cookie);
    }

    @Override
    public Iterator<Cookie> iterator() {
        return responseCookies.iterator();
    }

}
