package by.vsu.soa.ioay.service;

import java.util.List;

import by.vsu.soa.ioay.dao.MessageDao;
import by.vsu.soa.ioay.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao dao;

    @Override
    public Message getMessage(Long id) {
        return dao.read(id);
    }

    @Transactional
    @Override
    public void saveMessage(Message msg) {
        if (msg.getId() == null) {
            dao.create(msg);
        } else {
            dao.update(msg);
        }
    }

    @Override
    public List<Message> getMessageIn(String login) {
        return dao.findByTo(login);
    }

    @Override
    public List<Message> getMessageOut(String login) {
        return dao.findByFrom(login);
    }
}
