package controller;

import db.Database;
import model.User;
import webserver.exception.BadRequestException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.request.HttpRequest;

import java.util.Arrays;

import static utils.StringUtils.getDecodedString;

public class SignUpController implements Controller {

    private String userId;
    private String password;
    private String name;
    private String email;

    private static final int SIGN_UP_PARAMS_LENGTH = 4;
    private static final int KEY_VALUE_PAIR_LENGTH = 2;
    private static final int VALUE_INDEX = 1;

    @Override
    public void execute(HttpRequest request) {
        Database.addUser(new User(userId, password, name, email));
    }

    @Override
    public void verifyRequest(HttpRequest request){
        String[] bodys = request.getBody().split("[&]");

        if(bodys.length != SIGN_UP_PARAMS_LENGTH) {
            throw new BadRequestException();
        }
        parseBody(bodys);
    }

    private void parseBody(String[] bodys) {
        for (String body : bodys) {
            String[] param = body.split("[=]");

            if(param.length != KEY_VALUE_PAIR_LENGTH) {
                throw new BadRequestException();
            }

            setParams(param);
        }
    }

    private void setParams(String[] param) {
        switch (param[0]) {
            case "userId":
                userId = getDecodedString(param[VALUE_INDEX]);
                break;
            case "password":
                password = getDecodedString(param[VALUE_INDEX]);
                break;
            case "name":
                name = getDecodedString(param[VALUE_INDEX]);
                break;
            case "email":
                email = getDecodedString(param[VALUE_INDEX]);
                break;
            default:
                throw new BadRequestException();
        }
    }

    @Override
    public void manageResponse(HttpResponse response) {
        response.setStatus(HttpResponseStatus.STATUS_302);
        response.setHeader("Location","/index.html");
    }



}
