package com.ms.service;

import java.util.List;

import com.ms.entity.Address;
import com.ms.entity.Order;
import com.ms.entity.User;
import com.ms.exception.OrderException;

public interface OrderService  {
	
	
	public Order createOrder(User user, Address shippingAddress);
	
	public Order findOrderById(long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(long userId);
	
	public Order placedOrder(long orderId) throws OrderException;
	
	public Order confirmedOrder(long orderId) throws OrderException;
	
	public Order shippingOrder(long orderId) throws OrderException;
	
	public Order deliveredOrder(long orderId) throws OrderException;
	
	public Order cancledOrder(long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(long orderId) throws OrderException;
}
