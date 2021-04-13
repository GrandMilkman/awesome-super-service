package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.dao.GroupDao;
import by.vsu.soa.ioay.dao.RoleDao;
import by.vsu.soa.ioay.dao.UserDao;
import by.vsu.soa.ioay.entity.Group;
import by.vsu.soa.ioay.entity.Role;
import by.vsu.soa.ioay.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupDao groupDao;

    @Override
    public User getUser(final Long id) {
        return userDao.read(id);
    }

    @Override
    public User getUser(final String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    //@PreAuthorize("hasRole('admin')")
    @Transactional
    @Override
    public void saveUser(final User user) {
        if (user.getId() == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }
    
    @Transactional
    @Override
    public Group getGroup(final String name) {
        Group group=groupDao.findByName(name);
        if (group == null) {
            group = new Group(name);
            groupDao.create(group);
        }
        System.out.println(group.toString());
        return group;
    }

    //@PreAuthorize("hasRole('admin')")
    @Transactional
    @Override
    public void saveUser(final User user, final Long[] rid, final Long[] gid) {
        saveUser(user);
        groupDao.deleteAll(user.getId());
        Group userGroup = getGroup(user.getName());
        roleDao.deleteAll(userGroup.getId());

        if (rid != null) {
            for (Long id:rid) {
                roleDao.addRole(userGroup.getId(), id);
            }
            if (gid != null) {
                for (Long id : gid) {
                    groupDao.addGroup(user.getId(), id);
                }
            }
            groupDao.addGroup(user.getId(), userGroup.getId());
        }
    }

    //@PreAuthorize("hasRole('admin')")
    @Transactional
    @Override
    public void deleteUser(final Long id) {
    	Group group = groupDao.findByName(userDao.read(id).getName());
        groupDao.delete(group.getId());
        userDao.delete(id);
    }

    @Override
    public List<Group> getGroups() {
    	return groupDao.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Override
    public List<User> getMembersGroup(final Long id) {
        return userDao.findAllMembers(id);
    }
}
