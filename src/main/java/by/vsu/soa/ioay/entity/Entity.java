package by.vsu.soa.ioay.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @Getter @Setter
    private Long id;
}
