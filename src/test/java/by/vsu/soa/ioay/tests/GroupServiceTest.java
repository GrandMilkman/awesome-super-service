package by.vsu.soa.ioay.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;
import by.vsu.soa.ioay.service.GroupService;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService srv;

    @Test
    public void getGroups() {
        assertNotNull(srv.getGroups());
    }

    @Test
    public void getGroupNull() {
        assertNotNull(srv.getGroup(1L));
    }

    @Test
    public void getGroupNull2() {
        assertNotNull(srv.getGroup("admin"));
    }

    @Test
    public void getRoles() {
        assertNotNull(srv.getRoles());
    }

    @Test
    public void saveGroup1() {
        assertDoesNotThrow(() -> srv.saveGroup(srv.getGroup("foo"), new Long[] { 2L }));
    }

    @Test
    public void saveGroup2() {
        assertDoesNotThrow(() -> srv.saveGroup(srv.getGroup("foo"), null));
    }

    @Test
    public void addGroup1() {
        assertDoesNotThrow(() -> srv.addGroup(srv.getGroup("user")));
    }

    @Test
    public void addGroup2() {
        Group group = new Group();
        group.setName("Test");
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");
        ArrayList<Role> list = new ArrayList<Role>();
        list.add(role);
        group.setRoles(list);
        assertDoesNotThrow(() -> srv.addGroup(group));
    }

    @Test
    public void deleteMember() {
        assertDoesNotThrow(() -> srv.deleteMember(1L, 1L));
    }

    @Test
    public void deleteGroup() {
        Group group = new Group();
        group.setName("TEST");
        srv.addGroup(group);
        assertDoesNotThrow(() -> srv.deleteGroup(group.getId()));
    }
} // class
