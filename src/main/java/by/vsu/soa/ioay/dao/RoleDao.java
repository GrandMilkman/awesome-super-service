package by.vsu.soa.ioay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import by.vsu.soa.ioay.entity.Role;

@Mapper
public interface RoleDao extends Dao<Role> {
    List<Role> findAll();

    Role get(Long id);

    void deleteAll(Long gid);

    void addRole(@Param("gid") Long gid, @Param("rid") Long rid);
}
