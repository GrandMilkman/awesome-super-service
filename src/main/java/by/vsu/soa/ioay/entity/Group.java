package by.vsu.soa.ioay.entity;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Group extends Entity {

    private static final long serialVersionUID = 1L;

    @Getter @Setter @NonNull
    private String name;

    @Getter @Setter
    private List<Role> roles;
}
