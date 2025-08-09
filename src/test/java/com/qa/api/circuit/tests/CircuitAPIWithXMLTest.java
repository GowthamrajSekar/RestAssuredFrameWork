package com.qa.api.circuit.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XmlPathUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CircuitAPIWithXMLTest extends BaseTest {
	
	@Test
	public void getCirucitInfoTest() {
		
		Response response = restClient.get(BASE_URL_ERGAST_CIRCUIT, ERGAST_CIRCUIT__ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		List<String> circuitName =XmlPathUtils.readList(response, "MRData.CircuitTable.Circuit.CircuitName");	
		System.out.println(circuitName);
	
	}
	

}
