package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstant;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] getTestData() {
		return new Object[][] {
			{"Drogon", "Male", "active"},
			{"Rhegaon", "Male", "inactive"},
			{"Viserys", "Male", "inactive"}
		};
	}
	
	@DataProvider
	public Object[][] getExcelData(){
		return ExcelUtils.readData(AppConstant.CREATE_USER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getExcelData")
	public void createUserTest(String name, String gender, String status) {
		User user = new User(null,name,StringUtils.getRandomEmailId(), gender, status);				
		Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertNotNull(response.jsonPath().getString("id"));
		}
@Test
public void createUserTeswithString() {
	String email = StringUtils.getRandomEmailId();
	String  user = "{\r\n"
			+ "\"name\": \"khalesi\",\r\n"
			+ "\"gender\": \"Male\",\r\n"
			+ "\"email\": \""+email+"\",\r\n"
			+ "\"status\": \"Active\"\r\n"
			+ "}";
	Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertEquals(response.jsonPath().getString("name"), "khalesi");
	Assert.assertNotNull(response.jsonPath().getString("id"));
	}
@Test
public void createUserTeswithFile() throws IOException {
	String email = StringUtils.getRandomEmailId();
	//File userFile = new File("./src/test/resources/jsons/user.json");
	String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
	String updateJson = rawJson.replace("cataelynstarkss@got.com", email);
	
	Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, updateJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertEquals(response.jsonPath().getString("name"), "Catelyn Stark");
	Assert.assertNotNull(response.jsonPath().getString("id"));
	}

}
