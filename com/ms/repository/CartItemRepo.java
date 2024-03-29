package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.entity.Cart;
import com.ms.entity.CartItem;
import com.ms.entity.Product;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
	public CartItem isCartItemExists(@Param("cart")Cart cart,
			@Param("product")Product product,
			@Param("size")String size,
			@Param("userId")Long userId);
}
