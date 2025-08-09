package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUser extends BaseTest {
	
	
	@Test
	public void updateTest() {
		
		User user =User.builder()
		.id(null)
		.name("Grey Worm")
		.email(StringUtils.getRandomEmailId())
		.gender("Male")
		.status("Active")
		.build();
		
		Response responsePOST =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Grey Worm");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
		
		String userId = responsePOST.jsonPath().getString("id");
		System.out.println("User Id===> "+ userId);
		
		
		//2. Get User
		
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);
		
		
		//3 Update A USer
		
		
		user.setName("Doharis");
		user.setStatus("inactive");
		Response responsePUT = restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId , user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePUT.jsonPath().getString("name"), "Doharis");
		Assert.assertEquals(responsePUT.jsonPath().getString("status"), "inactive");
		Assert.assertEquals(responsePUT.jsonPath().getString("id"), userId);
		Assert.assertTrue(responsePUT.statusLine().contains("OK"));
		
		//4 Fetch same user ID;
		
		responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("name"), "Doharis");
		Assert.assertEquals(responseGET.jsonPath().getString("status"), "inactive");
		
		
	}

}
