package by.vsu.soa.ioay.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.vsu.soa.ioay.entity.User;
import by.vsu.soa.ioay.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService srv;

    @Test
    public void getUser1() {
        assertNotNull(srv.getUser(1L));
    }

    @Test
    public void getUser2() {
        assertNotNull(srv.getUser("admin"));
    }

    @Test
    public void getUsers() {
        assertNotNull(srv.getUsers());
    }

    @Test
    public void getGroups() {
        assertNotNull(srv.getGroups());
    }

    @Test
    public void getRoles() {
        assertNotNull(srv.getRoles());
    }

    @Test
    public void getMembersGroup() {
        assertNotNull(srv.getMembersGroup(1L));
    }

    @Test
    public void getGroup1() {
        assertNotNull(srv.getGroup("ADMINS"));
    }

    @Test
    public void getGroup2() {
        assertNotNull(srv.getGroup("TEST"));
    }

    @Test
    public void saveUser1() {
        assertDoesNotThrow(() -> srv.saveUser(srv.getUser("admin")));
    }

    @Test
    public void saveUser2() {
        User user = new User();
        user.setName("test");
        user.setPasswd("qwerty");
        assertDoesNotThrow(() -> srv.saveUser(user));
    }

    @Test
    public void saveUser3() {
        User user = new User();
        user.setName("test");
        user.setPasswd("qwerty");
        assertDoesNotThrow(() -> srv.saveUser(user, new Long[] { 2L }, new Long[] {2L}));
    }

    @Test
    public void saveUser4() {
        User user = new User();
        user.setName("test");
        user.setPasswd("qwerty");
        assertDoesNotThrow(() -> srv.saveUser(user, null , new Long[] {2L}));
    }

    @Test
    public void saveUser5() {
        User user = new User();
        user.setName("test");
        user.setPasswd("qwerty");
        assertDoesNotThrow(() -> srv.saveUser(user, new Long[] { 2L } , null ));
    }

} // class
