package application.presentation;

import application.service.UserService;
import application.service.dto.UserRequest;
import common.annotation.RequestMapping;
import common.annotation.RequestParam;
import common.annotation.Controller;

@Controller
public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @RequestMapping(value = "/user/create")
    public String create(
            @RequestParam(value = "userId") final String id,
            @RequestParam(value = "name") final String name,
            @RequestParam(value = "password") final String password,
            @RequestParam(value = "email") final String email
    ) {
        userService.create(new UserRequest(id, name, password, email));
        return "redirect:/index.html";
    }
}
