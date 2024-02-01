package com.ms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.*;
import com.ms.exception.OrderException;
import com.ms.repository.AddressRepo;
import com.ms.repository.OrderItemRepo;
import com.ms.repository.OrderRepo;
import com.ms.repository.UserRepo;
import com.ms.user.domain.OrderStatus;
import com.ms.user.domain.PaymentStatus;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepo orderRepo;
	@Autowired
	CartService cartService;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrderItemRepo orderItemRepo;
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address= addressRepo.save(shippingAddress);
		user.getAddress().add(address);
		userRepo.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item: cart.getCartItem()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			
			OrderItem createdOrderItem=orderItemRepo.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItem(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus(OrderStatus.PENDING);
		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepo.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepo.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order findOrderById(long orderId) throws OrderException {
		
		Optional<Order> opt=orderRepo.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(long userId) {
		List<Order> orders=orderRepo.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
		return order;
	}

	@Override
	public Order confirmedOrder(long orderId) throws OrderException {
		
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		return orderRepo.save(order);
	}

	@Override
	public Order shippingOrder(long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.DELIVERED);
		return orderRepo.save(order);
	}

	@Override
	public Order cancledOrder(long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepo.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void deleteOrder(long orderId) throws OrderException {
		findOrderById(orderId);
		orderRepo.deleteById(orderId);
		
	}

}
