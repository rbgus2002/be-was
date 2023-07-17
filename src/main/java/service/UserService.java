package service;

import db.Database;
import model.User;

import java.util.HashMap;
import java.util.Map;

import static model.User.*;
import static model.User.EMAIL;

public class UserService {

    public static void userSignUp(Map<String, String> parameterMap) throws NullPointerException {
        // User 객체 생성
        User user = new User(parameterMap.get(USERID),
                parameterMap.get(PASSWORD),
                parameterMap.get(NAME),
                parameterMap.get(EMAIL));
        // DB 저장
        Database.addUser(user);
    }

    public static void userSignUp(String body) throws NullPointerException {
        userSignUp(parseBodyParameter(body));
    }
    private static Map<String, String> parseBodyParameter(String body) {
        // &를 기준으로 파라미터 분할
        String[] bodyParameterList = body.split("&");
        // Map에 key-value 저장
        Map<String, String> bodyParameterMap = new HashMap<>();
        for(String bodyParameter: bodyParameterList) {
            bodyParameterMap.put(bodyParameter.split("=")[0],
                    bodyParameter.split("=")[1]);
        }

        return bodyParameterMap;
    }
}
