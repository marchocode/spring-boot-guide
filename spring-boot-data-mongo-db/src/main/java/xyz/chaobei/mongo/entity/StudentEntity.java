package xyz.chaobei.mongo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class StudentEntity {

    @Id
    private String id;

    private String name;

    private Integer sex;

    private Date createTime;

    private Date updateTime;


    public StudentEntity(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
