package controller;

import annotations.Controller;
import annotations.RequestMapping;
import dto.UserDTO;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import service.UserService;

@Controller
public class UserController {

    private final UserService userService = new UserService();

    //TODO: 파라미터가 잘 왔는지 확인하고 안온 값이 있으면 exception처리 해야함
    @RequestMapping(value = "/user/create", method = HttpMethod.GET)
    public String getUserCreate(HttpRequest request, HttpResponse response) {

        UserDTO userDTO = new UserDTO(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        userService.createUser(userDTO);

        return "/";
    }
}
