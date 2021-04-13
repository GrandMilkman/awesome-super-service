package by.vsu.soa.ioay.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import by.vsu.soa.ioay.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
public class UserForm {

    @Getter @Setter
    private String passwd;

    @Getter @Setter @NonNull
    private User user;

    @Getter @Setter
    private Long[] gid;

    @Getter @Setter
    private Long[] rid;

    public void clear() {
        passwd = null;
        user = null;
        gid = null;
    }
}
