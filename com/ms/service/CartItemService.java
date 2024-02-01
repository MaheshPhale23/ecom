package com.ms.service;

import com.ms.entity.Cart;
import com.ms.entity.CartItem;
import com.ms.entity.Product;
import com.ms.exception.CartItemException;
import com.ms.exception.UserException;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(long userId, long id, CartItem cartItem) throws UserException, CartItemException;
	
	public CartItem isCartItemExists(Cart cart, Product product, String size, long userId);
	
	public void removeCartItem(long userId, long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(long cartItemId) throws CartItemException;

}
