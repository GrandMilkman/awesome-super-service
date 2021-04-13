package by.vsu.soa.ioay.security;

import by.vsu.soa.ioay.entity.Message;
import by.vsu.soa.ioay.mvc.MessageController;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MessagePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (MessageController.MessageForm.class.isAssignableFrom(targetDomainObject.getClass())) {

            MessageController.MessageForm form = (MessageController.MessageForm) targetDomainObject;
            Message msg = form.getMessage();

            String name = authentication.getName();

            if (msg != null && msg.getFrom() != null && msg.getTo() != null) {
                return name.equals(msg.getFrom().getName()) || name.equals(msg.getTo().getName());
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
