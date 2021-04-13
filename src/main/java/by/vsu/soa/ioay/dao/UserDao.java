package by.vsu.soa.ioay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import by.vsu.soa.ioay.entity.User;

@Mapper
public interface UserDao extends CrudDao<Long, User> {
    User findByName(String name);

    List<User> findAll();

    List<User> findAllMembers(Long id);
}
