package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;

public interface GroupService {

    void addGroup(Group group);

    Group getGroup(Long id);

    Group getGroup(String name);

    void deleteGroup(Long id);

    List<Group> getGroups();

    List<Role> getRoles();

    void saveGroup(Group group, Long[] rid);

    void deleteMember(Long uid, Long gid);
}
