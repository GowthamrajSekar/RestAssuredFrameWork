package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
	
	private int id;
	private String title;
	private double price;
	private String description;
	private String image;
	private String category;
	private Rating rating;
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Rating{
		private double rate;
		private int count;
	}

}
