package com.ms.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ms.user.domain.OrderStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_id")
	private String orderId;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItem = new ArrayList<>();
	
	private LocalDateTime orderDate;
	
	private OrderStatus orderStatus;
	
	private LocalDateTime deliveryDate;
	
	@OneToOne
	private Address shippingAddress;

	@Embedded
	private PaymentDetails paymentDetails = new PaymentDetails();
	
	private double totalPrice;
	
	private int totalDiscountedPrice;
	
	private int discount;
	
	
	private int totalItem;
	
	private LocalDateTime createdAt;
}
