package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUsersTest extends BaseTest{

	@Test
	public void getAllUsers() {
		Response response=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	    Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void getAllUserWithQueryParamTest() {
		Map<String, String>queryParams = new HashMap<String, String>();
		queryParams.put("name", "Ajit Varman");
		queryParams.put("status", "active");
		Response response =restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	@Test(enabled=false)
	public void getSingleUserTest() {
		String userId ="7985367";
		Response response =restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("id"), userId);
	}
}
