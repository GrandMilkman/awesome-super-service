package by.vsu.soa.ioay.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper=true)
public class Role extends Entity {

    private static final long serialVersionUID = 1L;
    
    @Getter @Setter
    private String name;
}
