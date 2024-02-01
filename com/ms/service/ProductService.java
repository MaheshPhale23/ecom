package com.ms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ms.entity.Product;
import com.ms.exception.ProductException;
import com.ms.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(long product_id) throws ProductException;
	
	public List<Product> getAllProducts();
	
	public Product updateProduct(long product_id, Product req) throws ProductException;
	
	public Product findByProductById(long product_id) throws ProductException;
	
	public List<Product> searchProduct(String query);
	
	public List<Product> findProductByCategory(String category);
	
	public List<Product> recentlyAddedProduct();
	
	public Page<Product> geAllProduct(String category, List<String>color, List<String>sizes, int minPrice, int maxPrice,
			int minDiscount, String sort, String stock, int pageNumber, int pageSize);
	
}
