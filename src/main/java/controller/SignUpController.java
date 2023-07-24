package controller;

import db.Database;
import model.User;
import webserver.exception.BadRequestException;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import static utils.StringUtils.getDecodedString;

public class SignUpController implements Controller {

    @Override
    public void execute(HttpRequest request) {
        Database.addUser(new User(getDecodedString(request.getParamValueByKey("userId")), getDecodedString(request.getParamValueByKey("password")),
                getDecodedString(request.getParamValueByKey("name")), getDecodedString(request.getParamValueByKey("email"))));
    }

    @Override
    public void verifyRequest(HttpRequest request){
        if(request.getParamValueByKey("userId") == null || request.getParamValueByKey("password") == null ||
                request.getParamValueByKey("name") == null || request.getParamValueByKey("email") == null) {
            throw new BadRequestException();
        }
    }

    @Override
    public void manageResponse(HttpResponse response) {
        response.setBodyByText("SIGN UP SUCCESS");
    }



}
