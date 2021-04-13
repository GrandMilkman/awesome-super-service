package by.vsu.soa.ioay.entity;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class User extends Entity {

    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String passwd;

    @Getter @Setter
    private Boolean active;

    @Getter @Setter
    private Set<Role> roles;

    @Getter @Setter
    private List<Group> groups;
}
