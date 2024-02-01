package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{

	@Query("SELECT r FROM Review r WHERE r.product.id=:product_id")
	public List<Review> getAllProductsReview(@Param("product_id")Long product_id);
}
