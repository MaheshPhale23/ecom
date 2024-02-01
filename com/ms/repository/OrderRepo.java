package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED)")
	public List<Order> getUsersOrders(@Param("userId") Long userId);
	
	List<Order> findAllByOrderByCreatedAtDesc();
}
