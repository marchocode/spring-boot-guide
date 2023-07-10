package org.example;

import org.example.dao.OrderEntityRepository;
import org.example.dao.OrderItemEntityRepository;
import org.example.dao.OrderItemExtendEntityRepository;
import org.example.entity.OrderEntity;
import org.example.entity.OrderItemEntity;
import org.example.entity.OrderItemExtendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class DbTableShardingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DbTableShardingApplication.class, args);
    }

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Autowired
    private OrderItemEntityRepository orderItemEntityRepository;

    @Autowired
    private OrderItemExtendEntityRepository orderItemExtendEntityRepository;

    @Override
    public void run(String... args) throws Exception {

        for (long i = 0; i < 10; i++) {
            orderEntityRepository.save(new OrderEntity(i, String.format("name-%d", i)));
        }

        Optional<OrderEntity> byId = orderEntityRepository.findById(3l);
        System.out.println(byId.get());

        for (long i = 0; i < 10; i++) {
            orderItemEntityRepository.save(new OrderItemEntity(i, "2.00"));
            orderItemExtendEntityRepository.save(new OrderItemExtendEntity(i, String.format("a lot of characters.")));
        }


        Optional<OrderItemEntity> orderItem = orderItemEntityRepository.findById(6L);
        Optional<OrderItemExtendEntity> orderItemExtend = orderItemExtendEntityRepository.findById(6L);

        System.out.println(orderItem);
        System.out.println(orderItemExtend);

    }
}