package com.ms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entity.Product;
import com.ms.entity.Review;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.repository.ProductRepo;
import com.ms.repository.ReviewRepo;
import com.ms.request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	ReviewRepo reviewRepo;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepo productRepo;

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		
		Product product = productService.findByProductById(req.getProduct_id());
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReview(Long product_id) {
		
		return reviewRepo.getAllProductsReview(product_id);
	}

}
