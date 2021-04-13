package by.vsu.soa.ioay.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import by.vsu.soa.ioay.entity.User;
import by.vsu.soa.ioay.mvc.view.UserListExelView;
import by.vsu.soa.ioay.service.UserService;

@Controller
public class UserListExelController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private UserService srv;

    @Autowired
    private UserListExelView usersExelView;

    @RequestMapping("/users.xlsx")
    public ModelAndView users(final HttpServletRequest req, final Model model) {

        final List<User> users = srv.getUsers();

        model.addAttribute("users", users);

        model.addAttribute("_filename_", messages.getMessage("filename", null, req.getLocale()));

        return new ModelAndView(usersExelView, model.asMap());
    }
}
