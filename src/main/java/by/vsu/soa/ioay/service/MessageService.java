package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.entity.Message;

public interface MessageService {

    Message getMessage(Long id);

    void saveMessage(Message msg);

    List<Message> getMessageIn(String login);

    List<Message> getMessageOut(String login);
}
