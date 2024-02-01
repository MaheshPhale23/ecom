package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.service.ReviewService;
import com.ms.service.UserService;
import com.ms.entity.*;
import com.ms.exception.ProductException;
import com.ms.exception.UserException;
import com.ms.request.ReviewRequest;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user=userService.findUserProfileByJwt(jwt);
		System.out.println("product id "+req.getProduct_id()+" - "+req.getReview());
		Review review=reviewService.createReview(req, user);
		System.out.println("product review "+req.getReview());
		return new ResponseEntity<Review>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{product_id}")
	public ResponseEntity<List<Review>> getProductsReviewHandler(@PathVariable Long product_id){
		List<Review>reviews=reviewService.getAllReview(product_id);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
	}

}
