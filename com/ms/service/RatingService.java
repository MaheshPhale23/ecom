package com.ms.service;

import java.util.List;

import com.ms.entity.Rating;
import com.ms.entity.User;
import com.ms.exception.ProductException;
import com.ms.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest re, User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long product_id);
	
}
