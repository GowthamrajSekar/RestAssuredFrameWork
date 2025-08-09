package com.qa.api.amadeus.tests;


import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	private String accessToken;
	@BeforeMethod
	public void getOAuth2Token() {
		Response response=restClient.post(BASE_URL_OAUTH2_AMENDUS, AMADEUS_OAUTH2_TOKEN__ENDPOINT, ConfigManager.get("clientid"), ConfigManager.get("clientsecret"), ConfigManager.get("granttype"), ContentType.URLENC);
		response.prettyPrint();
		accessToken = response.jsonPath().getString("access_token");
		System.out.println("Access Token "+accessToken);
		ConfigManager.set("bearer", accessToken);
	
	}
	
	@Test
	public void getFlightDetailsTest() {
		//Maps.of("origin", "PAR", "maxPrice", "200");
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("origin", "PAR");
		queryparams.put("maxPrice", "200");
		Response responseGet =restClient.get(BASE_URL_OAUTH2_AMENDUS, AMADEUS_FLIGHT_DEST__ENDPOINT, queryparams, null, AuthType.BEARER_TOKEN, ContentType.URLENC);
		responseGet.prettyPrint();
	}

}
