package by.vsu.soa.ioay.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BCrypt {

    public static String bcrypt(final String password) {
        return password == null ? null : new BCryptPasswordEncoder().encode(password);
    }
}
