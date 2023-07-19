package controller;

import db.Database;
import model.User;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import static utils.StringUtils.getDecodedString;

public class SignUpController implements Controller {

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        Database.addUser(new User(getDecodedString(request.getParamValueByKey("userId")), getDecodedString(request.getParamValueByKey("password")),
                getDecodedString(request.getParamValueByKey("name")), getDecodedString(request.getParamValueByKey("email"))));
    }

    @Override
    public void verifyRequest(HttpRequest request){

    }

    @Override
    public void manageResponse(HttpResponse response){

    }



}
