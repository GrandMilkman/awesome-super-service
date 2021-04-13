package by.vsu.soa.ioay.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.vsu.soa.ioay.entity.Message;
import by.vsu.soa.ioay.service.MessageService;
import by.vsu.soa.ioay.service.UserService;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService srv;
    
    @Autowired
    private UserService userSrv;
    
    @Test
    public void getMessageNull() {
        assertNull(srv.getMessage(new Long(0)));
    }

    @Test
    public void getMessageNotNull() {
        assertNotNull(srv.getMessage(new Long(1)));
    }

    @Test
    public void getMessageInNull() {
        assertEquals(0,srv.getMessageIn(new String()).size());
    }

    @Test
    public void getMessageInNotNull() {
        assertNotNull(srv.getMessageIn("admin"));
    }

    @Test
    public void getMessageOutNull() {
        assertEquals(0,srv.getMessageOut(new String()).size());
    }

    @Test
    public void getMessageOutNotNull() {
        assertNotNull(srv.getMessageOut("admin"));
    }

    @Test
    public void saveMessageUpd() {
    	Message msg=new Message();
        msg.setSubject("test");
        msg.setText("text");
        msg.setId(1L);
        srv.saveMessage(msg);
        assertTrue(msg.equals(srv.getMessage(1L)));
    }

    @Test
    public void saveMessageCrt() {
        Message msg=new Message();
        msg.setSubject("test");
        msg.setText("text");
        msg.setFrom(userSrv.getUser(1L));
        msg.setTo(userSrv.getUser(2L));
        srv.saveMessage(msg);
        assertTrue(msg.equals(srv.getMessage(4L)));
    }
}
