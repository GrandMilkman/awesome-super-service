package by.vsu.soa.ioay.mvc;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.vsu.soa.ioay.entity.Message;
import by.vsu.soa.ioay.service.MessageService;

@Controller
public class MessageController {

    @Autowired
    private MessageService srv;

    @Autowired
    private MessageForm form;

    @Autowired
    private MessageFormValidator validator;

    @InitBinder("form")
    public void initBinding(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute("form")
    public MessageForm requestMessage(@RequestParam(value = "id", required = false) final Long id) {
        if (id == null) {
            form.setMessage(null);
        } else {
            form.setMessage(srv.getMessage(id));
        }

        return form;
    }

    @PreAuthorize("hasPermission(#form, 'READ')")
    @GetMapping("/message")
    public String message(@ModelAttribute("form") MessageForm form) {
        return "message";
    }

    @PreAuthorize("hasPermission(#form, 'WRITE')")
    @PostMapping("/message")
    public String save(@Valid @ModelAttribute("form") final MessageForm form, final BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "form", result);
            final Long mid = form.getMessage().getId();
            return "redirect:/message.html" + (mid == null ? "" : "?id=" + mid);
        } else {
            srv.saveMessage(form.getMessage());
            return "redirect:/index.html";
        }
    }

    @Component
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static class MessageForm {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    @Component
    public static class MessageFormValidator implements Validator {

        @Override
        public boolean supports(final Class<?> clazz) {
            return MessageForm.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(final Object target, final Errors errors) {
            final MessageForm form = (MessageForm) target;

            if (form.getMessage().getSubject().trim().isEmpty()) {
                errors.rejectValue("message.subject", "error.message.empty.subject");
            }

            if (form.getMessage().getText().trim().isEmpty()) {
                errors.rejectValue("message.text", "error.message.empty.text");
            }
        }
    }
}
