package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;


import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest{
	@Test
	public void getProducts() {
		
		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCT__ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		Product[] products =  JsonUtils.deserialize(response, Product[].class);
		for(Product product: products) {
			System.out.println("ID  "+ product.getId());
			System.out.println("Title  "+ product.getTitle());
			System.out.println("Price "+ product.getPrice());
			System.out.println("Description  "+ product.getDescription());
			System.out.println("Category  "+ product.getCategory());
			System.out.println("Image  "+ product.getImage());
			System.out.println("Count  "+ product.getRating().getCount());
			System.out.println("Rate  "+ product.getRating().getRate());
		
	}
		
	}
	 
	
	

}
