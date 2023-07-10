package org.example.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "t_order_item")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemEntity {

    @Id
    private Long itemId;

    private String value;



}
