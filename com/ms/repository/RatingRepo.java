package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.entity.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long>{
	
	@Query("SELECT r FROM Rating r WHERE r.product.id=:product_id")
	public List<Rating> getAllProductsRating(@Param("product_id") Long product_id);

}
