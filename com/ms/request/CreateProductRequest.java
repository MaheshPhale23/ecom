package com.ms.request;

import java.util.HashSet;
import java.util.Set;

import com.ms.entity.Sizes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	
	private String title;
	private String description;
	private int price;
	private int dicountedPrice;
	private int discountPercent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Sizes> size = new HashSet<>();
	private String imageUrl;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
}
