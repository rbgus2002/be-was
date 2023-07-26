package controller;

import annotations.Controller;
import annotations.RequestMapping;
import dto.UserDTO;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import service.UserService;

@Controller
public class UserController {

    private final UserService userService = new UserService();

    //TODO: 파라미터가 잘 왔는지 확인하고 안온 값이 있으면 exception처리 해야함
    @RequestMapping(value = "/user/create", method = HttpMethod.POST)
    public String getUserCreate(HttpRequest request, HttpResponse response, UserDTO userDTO) {

        userService.createUser(userDTO);

        response.setStatus(HttpStatus.CREATED);
        return "redirect:/";
    }
}
