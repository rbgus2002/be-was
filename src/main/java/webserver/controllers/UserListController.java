package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.HttpResponseStatus.FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/user/list.html")
public class UserListController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();

        if (!isValidCookie(request.cookie())) {
            return builder
                    .version(request.version())
                    .status(FOUND)
                    .redirect("/user/login.html")
                    .build();
        }

//        String listHtml;
//        try {
//            listHtml = Files.readString(Paths.get("src/main/resources/templates/user/list.html"));
//        } catch (IOException e) {
//            return createErrorResponse(request, NOT_FOUND);
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        int i = 1;
//        for(User user: findAll()) {
//            stringBuilder.append(String.format("<tr><th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>"
//                    , i++,user.getUserId(), user.getName(), user.getEmail()));
//        }
//        logger.debug(stringBuilder.toString());
//        byte[] body = listHtml.replace("<tr><th scope=\"row\">1</th> <td>javajigi</td> <td>자바지기</td> <td>javajigi@sample.net</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>"
//                ,stringBuilder.toString())
//                .getBytes();

        return builder.version(request.version())
                .status(OK)
                .fileName("src/main/resources/templates/user/list.html")
                .build();
    }
}
