package com.ms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.Product;
import com.ms.entity.Rating;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.repository.RatingRepo;
import com.ms.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	RatingRepo ratingRepo;
	
	@Autowired
	ProductService productService;
	

	@Override
	public Rating createRating(RatingRequest re, User user) throws ProductException {
		
		Product product = productService.findByProductById(re.getProduct_id());
		
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(re.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long product_id) {
		
		return ratingRepo.getAllProductsRating(product_id);
	}

}
