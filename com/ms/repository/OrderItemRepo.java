package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
