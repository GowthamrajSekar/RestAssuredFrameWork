package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JSONPathValidatiorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPath extends BaseTest {
	
	@Test
	public void getProducts() {
		
		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCT__ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		List<Number> ids=JSONPathValidatiorUtil.readList(response, "$[?(@.price>50)].id");
		System.out.println(ids);
		
		List<Number> prices=JSONPathValidatiorUtil.readList(response, "$[?(@.price>50)].price");
		System.out.println(prices);
		
		List<Number> rates=JSONPathValidatiorUtil.readList(response, "$[?(@.price>50)].rating.rate");
		System.out.println(rates);
		
		List<Number> counts=JSONPathValidatiorUtil.readList(response, "$[?(@.price>50)].rating.count");
		System.out.println(counts);
		
		List<Map<String, Object>> idTitleList=JSONPathValidatiorUtil.readListOfMaps(response, "$[*].['id','title']");
		System.out.println(idTitleList);
		
		
		List<Map<String, Object>> idCategoryList=JSONPathValidatiorUtil.readListOfMaps(response, "$[*].['id','category']");
		System.out.println(idCategoryList);
		
		List<Map<String, Object>> idCategory=JSONPathValidatiorUtil.readListOfMaps(response, "$[*].['id','category']");
		System.out.println(idCategory);
		
		List<Map<String, Object>> JewPriceTitle=JSONPathValidatiorUtil.readListOfMaps(response, "$[?(@.category=='electronics')].['price', 'title']");
		System.out.println(JewPriceTitle);
		
		
		Double minPrice = JSONPathValidatiorUtil.read(response, "min($[*].price)");
		System.out.println("Min Price-----> "+minPrice);
		
		
		
	}

}
