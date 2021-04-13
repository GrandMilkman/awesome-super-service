package by.vsu.soa.ioay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import by.vsu.soa.ioay.entity.Group;

@Mapper
public interface GroupDao extends CrudDao<Long, Group> {
    List<Group> findAll();

    Group findByName(String name);

    void deleteAll(Long uid);

    void addGroup(@Param("uid") Long uid, @Param("gid") Long gid);

    void deleteFromGroup(@Param("uid") Long uid, @Param("gid") Long gid);
}
