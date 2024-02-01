package com.ms.service;

import java.util.List;

import com.ms.entity.Review;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReview(Long product_id);

}
