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

    @Override
    public void execute(HttpRequest request) {
        Database.addUser(new User(userId, password, name, email));
    }

    @Override
    public void verifyRequest(HttpRequest request){
        String[] bodys = request.getBody().split("[&]");

        if(bodys.length != 4) {
            throw new BadRequestException();
        }
        parseBody(bodys);
    }

    private void parseBody(String[] bodys) {
        for (String body : bodys) {
            String[] param = body.split("[=]");

            if(param.length != 2) {
                throw new BadRequestException();
            }

            setParams(param);
        }
    }

    private void setParams(String[] param) {
        switch (param[0]) {
            case "userId":
                userId = getDecodedString(param[1]);
                break;
            case "password":
                password = getDecodedString(param[1]);
                break;
            case "name":
                name = getDecodedString(param[1]);
                break;
            case "email":
                email = getDecodedString(param[1]);
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
