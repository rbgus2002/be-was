package controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.Database;
import model.User;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static utils.StringUtils.getDecodedString;

public class UserController {


    public void signUp(HttpRequest request, HttpResponse response) {
        Database.addUser(new User(getDecodedString(request.getParamValueByKey("userId")), getDecodedString(request.getParamValueByKey("password")),
                getDecodedString(request.getParamValueByKey("name")), getDecodedString(request.getParamValueByKey("email"))));
    }

}
