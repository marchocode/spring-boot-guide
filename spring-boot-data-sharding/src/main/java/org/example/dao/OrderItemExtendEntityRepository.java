package org.example.dao;

import org.example.entity.OrderItemExtendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemExtendEntityRepository extends JpaRepository<OrderItemExtendEntity, Long> {


}
