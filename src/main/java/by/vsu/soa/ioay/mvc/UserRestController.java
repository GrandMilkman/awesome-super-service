package by.vsu.soa.ioay.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.vsu.soa.ioay.entity.User;
import by.vsu.soa.ioay.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService srv;

    @GetMapping
    public List<User> users() {
        return srv.getUsers();
    }

    @GetMapping("/{id}")
    public User user(@PathVariable("id") Long id) {
        return srv.getUser(id);
    }

} // class
