package by.vsu.soa.ioay.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Message extends Entity {

    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private String subject;

    @Getter @Setter
    private String text;

    @Getter @Setter
    private Date date;

    @Getter @Setter
    private User from;

    @Getter @Setter
    private User to;
}
