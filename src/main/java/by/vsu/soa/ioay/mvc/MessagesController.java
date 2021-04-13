package by.vsu.soa.ioay.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import by.vsu.soa.ioay.entity.Message;
import by.vsu.soa.ioay.service.MessageService;

@Controller
public class MessagesController {

    @Autowired
    private MessageService srv;

    @GetMapping("/index")
    public String messages(Authentication auth, Model model) {

        List<Message> in = srv.getMessageIn(auth.getName());

        if (in != null) {
            model.addAttribute("in", in);
        }

        List<Message> out = srv.getMessageOut(auth.getName());

        if (out != null) {
            model.addAttribute("out", out);
        }

        return "index";
    }
}
