package com.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.service.CartService;
import com.ms.service.UserService;
import com.ms.exception.ProductException;
import com.ms.entity.CartItem;
import com.ms.request.AddItemRequest;
import com.ms.exception.UserException;
import com.ms.entity.Cart;
import com.ms.entity.User;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		
		System.out.println("cart - "+cart.getUser().getEmail());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req, 
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		CartItem item = cartService.addCartItem(user.getId(), req);
		
//		ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);
		
		return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
		
	}

}
