package com.qa.api.gorest.tests;

import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUser extends BaseTest{
	
	@Test
	public void deleteTest() {
		
		User user =User.builder()
		.name("Benjamin Stark")
		.email(StringUtils.getRandomEmailId())
		.gender("Male")
		.status("inactive")
		.build();
		
		Response responsePOST =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Benjamin Stark");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
		
		String userId = responsePOST.jsonPath().getString("id");
		System.out.println("User Id===> "+ userId);
		
		
		//2. Get User
		
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);
		
		
		//3 Delete A USer
		
		Response responseDelete = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId , null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseDelete.statusLine().contains("No Content"));
		
		
		//4 Fetch same user ID;
		
		responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGET.jsonPath().getString("message"), "Resource not found");
		Assert.assertTrue(responseGET.statusLine().contains("404 Not Found"));
				
		
	}

			
	
}
