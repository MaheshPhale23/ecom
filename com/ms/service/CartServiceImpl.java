package com.ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.Cart;
import com.ms.entity.CartItem;
import com.ms.entity.Product;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.repository.CartRepo;
import com.ms.request.AddItemRequest;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartItemService cartItemService;
	
	@Autowired
	ProductService productService;


	@Override
	public Cart createCart(User user) {
		
		Cart cart = new Cart();
		cart.setUser(user);
		
		return cartRepo.save(cart);
	}

	@Override
	public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart = cartRepo.findByUserId(userId);
		Product product = productService.findByProductById(req.getProduct_id());
		
		CartItem isPresent = cartItemService.isCartItemExists(cart, product, req.getSize(), userId);
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdItem = cartItemService.createCartItem(cartItem);
			cart.getCartItem().add(createdItem);
			return createdItem;
		}
		
		return isPresent;
	}

	@Override
	public Cart findUserCart(long userId) {
		
		Cart cart = cartRepo.findByUserId(userId);
		
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;
		
		for(CartItem cartItem : cart.getCartItem()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);
		
		return cartRepo.save(cart);
	}


}
