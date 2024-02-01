package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{

	@Query("SELECT c FROM Cart c WHERE c.user.id=:userId")
	public Cart findByUserId(@Param("userId") long userId);

}
