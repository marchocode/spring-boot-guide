package xyz.chaobei.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User implements Serializable {

    private Long id;

    private String name;

}
