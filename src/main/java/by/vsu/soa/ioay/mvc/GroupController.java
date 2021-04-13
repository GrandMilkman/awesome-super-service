package by.vsu.soa.ioay.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;
import by.vsu.soa.ioay.entity.User;
import by.vsu.soa.ioay.service.GroupService;
import by.vsu.soa.ioay.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupSrv;

    @Autowired
    private UserService userSrv;

    @Autowired
    private GroupForm form;

    private final Validator validator = new GroupFormValidator();

    @InitBinder("form")
    public void initBinding(final WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute("roles")
    public List<Role> requestRoles() {
        return groupSrv.getRoles();
    }

    @ModelAttribute("form")
    public GroupForm requestGroup(@PathVariable(name = "id", required = false) final Long id) {
        form.clear();
        if (id == null) {
            form.setGroup(new Group());
        } else {
            form.setGroup(groupSrv.getGroup(id));
        }

        return form;
    }

    @ModelAttribute("members")
    public List<User> requestMembers(@PathVariable(name = "id", required = false) final Long id) {
        if (id != null) {
            return userSrv.getMembersGroup(id);
        }
        return new ArrayList<User>();
    }

    @RequestMapping(path = "/{id}/{idmembr}", method = RequestMethod.GET)
    public String deleteMembr(@PathVariable(name = "id", required = true) final Long idGroup,
            @PathVariable(name = "idmembr", required = true) final Long idMembr) {
        groupSrv.deleteMember(idMembr, idGroup);
        return "redirect:/groups/" + idGroup;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/", "/{id:\\d+}" }, method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("form") final GroupForm form, final BindingResult result,
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "form", result);
            return "redirect:/groups/" + (form.getGroup().getId() == null ? "new" : form.getGroup().getId());
        } else {
            groupSrv.saveGroup(form.getGroup(), form.getRid());
            form.clear();
            return "redirect:/groups";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/new", "/{id:\\d+}" }, method = RequestMethod.GET)
    public String editGroup() {
        return "group";
    }

    @Component
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static class GroupForm {
        private Group group;

        private Long[] rid;

        public Group getGroup() {
            return group;
        }

        public void setGroup(final Group group) {
            this.group = group;
        }

        public void setRid(final Long[] rid) {
            this.rid = rid;
        }

        public Long[] getRid() {
            return rid;
        }

        public void clear() {
            group = null;
            rid = null;
        }
    }

    @Component
    public static class GroupFormValidator implements Validator {

        @Override
        public boolean supports(final Class<?> clazz) {
            return GroupForm.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            final GroupForm form = (GroupForm) target;

            if (form.getGroup().getName().trim().isEmpty()) {
                errors.rejectValue("group.name", "error.group.empty.name");
            }
        }
    }
}
