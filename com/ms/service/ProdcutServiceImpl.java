 package com.ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms.entity.Category;
import com.ms.entity.Product;
import com.ms.exception.ProductException;
import com.ms.repository.CategoryRepo;
import com.ms.repository.ProductRepo;
import com.ms.request.CreateProductRequest;

@Service
public class ProdcutServiceImpl implements ProductService{
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public Product createProduct(CreateProductRequest req) {
		
		Category top_level = categoryRepo.findByName(req.getTopLevelCategory());
		
		if(top_level == null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			top_level = categoryRepo.save(topLevelCategory);
		}
		
		Category second_level = categoryRepo.findByNameAndParent(req.getSecondLevelCategory(), top_level.getName());
		
		if(second_level == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(top_level);
			secondLevelCategory.setLevel(2);
			
			second_level = categoryRepo.save(secondLevelCategory);
		}
		
		Category third_level = categoryRepo.findByNameAndParent(req.getThirdLevelCategory(), second_level.getName());
		
		if(third_level==null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(second_level);
			thirdLevelCategory.setLevel(3);
			
			third_level = categoryRepo.save(thirdLevelCategory);
		}
		
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDicountedPrice());
		product.setDiscountedPercent(req.getDiscountPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(third_level);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = productRepo.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(long product_id) throws ProductException {
		Product product = findByProductById(product_id);
		product.getSizes().clear();
		productRepo.delete(product);
		return null;
	}

	@Override
	public Product updateProduct(long product_id, Product req) throws ProductException {
		
		Product product = findByProductById(product_id);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		
		return productRepo.save(product);
	}

	@Override
	public Product findByProductById(long product_id) throws ProductException {

		Optional<Product> opt = productRepo.findById(product_id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id - "+product_id);
		
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		System.out.println("category --- "+category);
		
		List<Product> products = productRepo.findByCategory(category);
		return products;
	}

	@Override
	public Page<Product> geAllProduct(String category, List<String> color, List<String> sizes, int minPrice,
			int maxPrice, int minDiscount, String sort, String stock, int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepo.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if(color.isEmpty()) {
			products = products.stream().filter(p-> color.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
			
			
		}
		
		if(stock != null) {
			if(stock.equals("in_stock")){
				products.stream().filter(p-> p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p-> p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
		
		return filteredProducts;
	}

	@Override
	public List<Product> getAllProducts() {
		
		return productRepo.findAll();
	}

	@Override
	public List<Product> recentlyAddedProduct() {
		return productRepo.findTop10ByOrderByCreatedAtDesc();
	}

	@Override
	public List<Product> searchProduct(String query) {
		
		List<Product> products=productRepo.searchProduct(query);
		return products;
	}

}
