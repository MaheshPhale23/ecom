package com.ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.OrderItem;
import com.ms.repository.OrderItemRepo;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	
	@Autowired
	OrderItemRepo orderItemRepo;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepo.save(orderItem);
	}

}
