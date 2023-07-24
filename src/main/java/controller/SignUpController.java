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

    private String userId;
    private String password;
    private String name;
    private String email;

    private static final int SIGN_UP_PARAMS_LENGTH = 4;
    private static final int KEY_VALUE_PAIR_LENGTH = 2;
    private static final int VALUE_INDEX = 1;

    @Override
    public void execute(HttpRequest request) {
        if(Database.findUserById(userId) != null) {
            throw new ConflictException("이미 존재하는 아이디입니다!");
        }
        Database.addUser(new User(userId, password, name, email));
        //중복처리
        //동시성 처리 어떻게?
    }

    @Override
    public void verifyRequest(HttpRequest request){
        String[] bodys = request.getBody().split("[&]");

        if(bodys.length != SIGN_UP_PARAMS_LENGTH) {
            throw new BadRequestException("파라미터의 개수가 잘못되었습니다!");
        }
        parseBody(bodys);
    }

    private void parseBody(String[] bodys) {
        //바디 파싱을 여기서 하는게 맞을까?
        for (String body : bodys) {
            String[] param = body.split("[=]");

            if(param.length != KEY_VALUE_PAIR_LENGTH) {
                throw new BadRequestException("Body의 형식이 잘못되었습니다!");
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
                throw new BadRequestException("알맞은 key값을 넣어주세요!");
        }
    }

    @Override
    public void manageResponse(HttpResponse response) {
        response.setStatus(HttpResponseStatus.STATUS_302);
        response.setHeader("Location","/index.html");
    }



}
