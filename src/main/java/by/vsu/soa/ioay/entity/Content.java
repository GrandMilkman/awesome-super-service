package by.vsu.soa.ioay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Content extends Entity {

    private static final long serialVersionUID = 1L;
    
    @Getter @Setter
    private String name;

    @Getter @Setter
    private Long size;

    @Getter @Setter
    private String contentType;

    @Getter @Setter
    private String desc;

    @Getter @Setter
    private Long ownerId;
}
