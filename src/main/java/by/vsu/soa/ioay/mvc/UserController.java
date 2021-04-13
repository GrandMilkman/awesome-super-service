package by.vsu.soa.ioay.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService srv;

    @Autowired
    private GroupService groupSrv;

    @Autowired
    private UserForm form;

    private Validator validator = new UserFormValidator();

    @InitBinder("form")
    public void initBinding(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute("roles")
    public List<Role> requestRoles() {
        return srv.getRoles();
    }

    @ModelAttribute("userGroupRoles")
    public List<Role> requestUserGroupRoles(@PathVariable(name = "id", required = false) final Long id) {
        if (id == null) {
            return new ArrayList<Role>();
        } else {
            return groupSrv.getGroup(srv.getUser(id).getName()).getRoles();
        }
    }

    @ModelAttribute("groups")
    public List<Group> requestGroups(@PathVariable(name = "id", required = false) final Long id) {
        List<Group> groups = srv.getGroups();
        if (id != null) {
            User user = srv.getUser(id);
            int indx = 0;
            for (Group gr: groups) {
                if (gr.getName().equals(user.getName())) {
                    indx = groups.indexOf(gr);
                }
            }
            groups.remove(indx);
        }
        return groups;
    }

    @ModelAttribute("form")
    public UserForm requestUser(@PathVariable(name = "id", required = false) final Long id) {
        form.clear();
        if (id == null) {
            form.setUser(new User());
        } else {
            form.setUser(srv.getUser(id));
        }
        return form;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/new", "/{id:\\d+}"}, method = RequestMethod.GET)
    public String edit() {
        return "user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/", "/{id:\\d+}"}, method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("form") final UserForm form, final BindingResult result, final RedirectAttributes redirectAttributes) {
        //validator.validate(form, result);

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "form", result);
            return "redirect:/users/" + (form.getUser().getId() == null ? "new" : form.getUser().getId());
        } else {
            srv.saveUser(form.getUser(), form.getRid(), form.getGid());
            form.clear();
            return "redirect:/users";
        }
    }

    private class UserFormValidator implements Validator {
        @Override
        public boolean supports(final Class<?> clazz) {
            return clazz.isInstance(form);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            UserForm form = (UserForm) target;

            if (form.getUser().getPasswd().trim().isEmpty()) {
                errors.rejectValue("user.passwd", "error.user.empty.password");
            }

            if (form.getPasswd().trim().isEmpty()) {
                errors.rejectValue("passwd", "error.user.empty.password");
            }

            if (!form.getPasswd().trim().equals(form.getUser().getPasswd().trim())) {
                errors.rejectValue("user.passwd", "error.user.empty.password");
                errors.rejectValue("passwd", "error.user.empty.password");
            }

            if (form.getUser().getName().trim().isEmpty()) {
                errors.rejectValue("user.name", "error.user.empty.name");
            }

            if (form.getUser().getId() == null && srv.getUser(form.getUser().getName()) != null) {
                errors.rejectValue("user.name", "error.user.duplicate.name", new Object[] {form.getUser().getName()}, "User exist!");
            }
        }
    }
}
