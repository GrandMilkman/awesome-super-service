package by.vsu.soa.ioay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import by.vsu.soa.ioay.entity.Message;

@Mapper
public interface MessageDao extends CrudDao<Long, Message> {

    List<Message> findByTo(String name);

    List<Message> findByFrom(String name);
    
}
