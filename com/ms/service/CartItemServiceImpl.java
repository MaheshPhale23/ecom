package com.ms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.Cart;
import com.ms.entity.CartItem;
import com.ms.entity.Product;
import com.ms.entity.User;
import com.ms.exception.CartItemException;
import com.ms.exception.UserException;
import com.ms.repository.CartItemRepo;
import com.ms.repository.CartRepo;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	CartItemRepo cartItemRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartRepo cartRepo;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepo.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(long userId, long id, CartItem cartItem) throws UserException, CartItemException {
		
		CartItem item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
			
			
		}
		
		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartItemExists(Cart cart, Product product, String size, long userId) {
		
		CartItem cartItem = cartItemRepo.isCartItemExists(cart, product, size, userId);
		
		return cartItem;
	}

	@Override
	public void removeCartItem(long userId, long cartItemId) throws CartItemException, UserException {
		
		CartItem cartItem = findCartItemById(cartItemId);
		User user = userService.findUserById(cartItem.getUserId());
		
		User reqUser = userService.findUserById(userId);
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepo.deleteById(cartItemId);
		}
		else {
			throw new UserException("You Can't remove another users item");
		}
		
	}

	@Override
	public CartItem findCartItemById(long cartItemId) throws CartItemException {
		Optional<CartItem> opt = cartItemRepo.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new CartItemException("CartItem not found with id : "+ cartItemId);
	}

}
