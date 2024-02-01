package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.entity.Product;
import com.ms.exception.ProductException;
import com.ms.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
			@RequestParam List<String>color, @RequestParam List<String>size, @RequestParam int minPrice,
			@RequestParam int maxPrice, @RequestParam int minDiscount, @RequestParam String sort,
			@RequestParam String stock, @RequestParam int pageNumber, @RequestParam int pageSize){
		
		Page<Product> res = productService.geAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, 
				stock, pageNumber, pageSize);
		
		System.out.println("complete products");
		
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/products/id/{product_id}")
	public ResponseEntity<Product> findProductByHandler(@PathVariable long product_id) throws ProductException{
		
		Product product = productService.findByProductById(product_id);
		
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
		
		List<Product> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
		
	}

}
