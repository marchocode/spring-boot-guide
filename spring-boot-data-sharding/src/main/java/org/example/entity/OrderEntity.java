package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dao.OrderItemEntityRepository;

import javax.persistence.*;

@Table(name = "t_order")
@Entity
@Getter
@Setter
@ToString
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    private String name;

    public OrderEntity() {

    }

    public OrderEntity(Long orderId, String name) {
        this.orderId = orderId;
        this.name = name;
    }
}
