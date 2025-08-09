package com.qa.api.contacts.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsAPICredential;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest{
	public String tokenID;
	
	@BeforeMethod
	public void getToken() {
		ContactsAPICredential credential = ContactsAPICredential.builder()
		.email("naveenanimation20@gmail.com")
		.password("test@123")
		.build();
		
		Response response =restClient.post(BASE_URL_CONTACTS, CONTACTS_USERS_ENDPOINT,credential, null, null, AuthType.NO_AUTH, ContentType.JSON);
		String tokenID =response.jsonPath().getString("token");
		ConfigManager.set("bearer", tokenID);
		System.out.println("Token ID =====> "+tokenID);
	}
	@Test
	public void getUser() {
		
		Response responseGET =restClient.get(BASE_URL_CONTACTS, CONTACTS__ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
	}

}
