package controller;

import db.Database;
import model.User;
import webserver.exception.BadRequestException;
import webserver.exception.ConflictException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.request.HttpRequest;

import java.util.Arrays;

import static utils.StringUtils.getDecodedString;

public class SignUpController implements Controller {

    private final int SIGN_UP_PARAMS_LENGTH = 4;
    private final int KEY_VALUE_PAIR_LENGTH = 2;
    private final int VALUE_INDEX = 1;

    private static SignUpController signUpController;

    private SignUpController() {

    }

    public static SignUpController getInstance() {
        if(signUpController == null) {
            signUpController = new SignUpController();
        }
        return signUpController;
    }

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        User user = new User();
        verifyRequest(request, user);
        addUser(user);
        manageResponse(response);
    }

    private void addUser(User user) {
        if(Database.findUserById(user.getUserId()) != null) {
            throw new ConflictException("이미 존재하는 아이디입니다!");
        }
        Database.addUser(user);
    }

    private void verifyRequest(HttpRequest request, User user){
        String[] bodies = request.getBody().split("[&]");

        if(bodies.length != SIGN_UP_PARAMS_LENGTH) {
            throw new BadRequestException("파라미터의 개수가 잘못되었습니다!");
        }

        parseBody(bodies, user);
    }

    private void parseBody(String[] bodys, User user) {
        //바디 파싱을 여기서 하는게 맞을까?
        for (String body : bodys) {
            String[] param = body.split("[=]");

            if(param.length != KEY_VALUE_PAIR_LENGTH) {
                throw new BadRequestException("Body의 형식이 잘못되었습니다!");
            }

            setParams(param, user);
        }
    }

    private void setParams(String[] param, User user) {
        switch (param[0]) {
            case "userId":
                user.setUserId(getDecodedString(param[VALUE_INDEX]));
                break;
            case "password":
                user.setPassword(getDecodedString(param[VALUE_INDEX]));
                break;
            case "name":
                user.setName(getDecodedString(param[VALUE_INDEX]));
                break;
            case "email":
                user.setEmail(getDecodedString(param[VALUE_INDEX]));
                break;
            default:
                throw new BadRequestException("알맞은 key값을 넣어주세요!");
        }
    }

    private void manageResponse(HttpResponse response) {
        response.setStatus(HttpResponseStatus.STATUS_302);
        response.setHeader("Location","/index.html");
    }



}
