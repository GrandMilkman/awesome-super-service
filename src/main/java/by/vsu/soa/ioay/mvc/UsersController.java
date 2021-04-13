package by.vsu.soa.ioay.mvc;

import java.util.List;

import by.vsu.soa.ioay.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import by.vsu.soa.ioay.service.UserService;

@Controller
public class UsersController {

    @Autowired
    private UserService srv;

    @RequestMapping("/users")
    public String index(Model model) {
        List<User> users = srv.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

}
