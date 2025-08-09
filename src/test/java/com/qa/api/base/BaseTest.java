package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
@Listeners(ChainTestListener.class)
public class BaseTest {
	
	protected RestClient restClient;
	
	//*********** API Base Urls************//
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_REQRES = "https://reqres.in/";
	protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
	protected final static String BASE_URL_OAUTH2_AMENDUS = "https://test.api.amadeus.com";
	protected final static String BASE_URL_ERGAST_CIRCUIT = "http://ergast.com";
	
	
	//*********** API Endpoint************//
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_USERS_ENDPOINT = "/users/login";
	protected final static String CONTACTS__ENDPOINT = "/contacts";
	protected final static String REQRES__ENDPOINT = "/api/users";
	protected final static String BASIC_AUTH__ENDPOINT = "/basic_auth";
	protected final static String PRODUCT__ENDPOINT = "/products";
	protected final static String AMADEUS_OAUTH2_TOKEN__ENDPOINT = "/v1/security/oauth2/token";
	protected final static String AMADEUS_FLIGHT_DEST__ENDPOINT = "/v1/shopping/flight_destinations";
	protected final static String ERGAST_CIRCUIT__ENDPOINT = "/api/f1/2017/circuits.xml";
	
	@BeforeSuite
	public void setupAllureREsport() {
		RestAssured.filters(new AllureRestAssured());
	}
	
	@BeforeTest
	public void setup(){
		restClient = new RestClient();
	}
}
