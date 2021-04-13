package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.dao.GroupDao;
import by.vsu.soa.ioay.dao.RoleDao;
import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public void addGroup(final Group group) {
        if (group.getId() == null) {
            groupDao.create(group);
        } else {
            groupDao.update(group);
        }
    }

    @Override
    public Group getGroup(final Long id) {
        return groupDao.read(id);
    }

    @Override
    public Group getGroup(final String name) {
        return groupDao.findByName(name);
    }

    @Transactional
    @Override
    public void deleteGroup(final Long id) {
        groupDao.delete(id);
    }

    @Override
    public List<Group> getGroups() {
        return groupDao.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Transactional
    @Override
    public void saveGroup(final Group group, final Long[] rid) {
        addGroup(group);
        roleDao.deleteAll(group.getId());

        if (rid != null) {
            for (Long id : rid) {
                roleDao.addRole(group.getId(), id);
            }
        }
    }

    @Transactional
    @Override
    public void deleteMember(final Long uid, final Long gid) {
        groupDao.deleteFromGroup(uid, gid);
    }
}
