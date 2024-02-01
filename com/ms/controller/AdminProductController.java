package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.entity.Product;
import com.ms.exception.ProductException;
import com.ms.request.CreateProductRequest;
import com.ms.response.ApiResponse;
import com.ms.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/")
	public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req) throws ProductException{
		
		Product createProduct = productService.createProduct(req);
		
		return new ResponseEntity<Product>(createProduct,HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/{product_id}/delete")
	public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable long product_id) throws ProductException{
		
		System.out.println("dlete product controller .... ");
		String msg=productService.deleteProduct(product_id);
		System.out.println("dlete product controller .... msg "+msg);
		ApiResponse res=new ApiResponse(msg,true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct(){
		
		List<Product> products = productService.getAllProducts();
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<Product>> recentlyAddedProduct(){
		
		List<Product> products = productService.recentlyAddedProduct();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@PutMapping("/{product_id}/update")
	public ResponseEntity<Product> updateProductHandler(@RequestBody Product req,@PathVariable Long product_id) throws ProductException{
		
		Product updatedProduct=productService.updateProduct(product_id, req);
		
		return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
	}
	
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest [] req) throws ProductException{
		
		for(CreateProductRequest product:req) {
			productService.createProduct(product);
		}
		ApiResponse res=new ApiResponse("products created successfully",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}

}
