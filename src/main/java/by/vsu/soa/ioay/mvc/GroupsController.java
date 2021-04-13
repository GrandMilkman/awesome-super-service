package by.vsu.soa.ioay.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.service.GroupService;

@Controller
public class GroupsController {

    @Autowired
    private GroupService groupSrv;

    @RequestMapping("/groups")
    public String groups(final Model model) {
        final List<Group> groups = groupSrv.getGroups();
        model.addAttribute("groups", groups);
        return "groups";
    }
}
