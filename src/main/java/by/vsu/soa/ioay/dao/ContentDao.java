package by.vsu.soa.ioay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import by.vsu.soa.ioay.entity.Content;

@Mapper
public interface ContentDao extends CrudDao<Long, Content> {

    Content findContent(@Param("id") Long id, @Param("username") String username);

    List<Content> findContentsByUserName(@Param("username") String username);
}
