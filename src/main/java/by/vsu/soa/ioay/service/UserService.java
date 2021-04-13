package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;
import by.vsu.soa.ioay.entity.User;

public interface UserService {

    User getUser(Long id);

    User getUser(String name);

    List<User> getUsers();

    void saveUser(User user);

    void saveUser(User user, Long[] rid, Long[] gid);

    void deleteUser(Long id);

    Group getGroup(String name);

    List<Group> getGroups();

    List<Role> getRoles();

    List<User> getMembersGroup(Long id);
}
