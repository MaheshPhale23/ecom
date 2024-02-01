package com.ms.service;

import com.ms.entity.Cart;
import com.ms.entity.CartItem;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(long userId);

}
