package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.entity.Order;
import com.ms.exception.OrderException;
import com.ms.service.OrderService;
import com.ms.response.ApiResponse;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable long orderId, 
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.confirmedOrder(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> ShippedOrderHandler(@PathVariable long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.shippingOrder(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliverOrderHandler(@PathVariable long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.cancledOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		
		orderService.deleteOrder(orderId);
		
		ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
		
		System.out.println("delete method working....");
		
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
}
