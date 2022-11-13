package xyz.chaobei.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class User implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 6)
    private String name;

    private Date date;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;
}
