package com.qa.api.client;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {

	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

	private RequestSpecification setupRequest(String bseUrl, AuthType authType, ContentType contentType) {

		RequestSpecification request = RestAssured.given().log().all().baseUri(bseUrl).contentType(contentType)
				.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearer"));
			break;
		case OAUTH2:
			request.header("Authorization", "Bearer " + "sometoken");
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("Authorization", "Bearer " + "sometoken");
			break;
		case NO_AUTH:
			System.out.println("No Auth required");
			break;
		default:
			System.out.println("This Auth is not supported....Please pass the right Authtype");
			throw new APIException("=====Invalid Auth========");

		}
		return request;

	}
	
	private String generateBasicAuthToken() {
		String credentials = ConfigManager.get("basicauthusername")+":"+ConfigManager.get("basicauthpassword");
		return Base64.getEncoder().encodeToString(credentials.getBytes());
		
	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams,
			Map<String, String> pathParams) {

		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		if (pathParams != null) {
			request.queryParams(pathParams);
		}
	}
//CRUD
	
//GET
	
/**
 * This method is used to call GET API 
 * @param baseUrl
 * @param endPoint
 * @param queryParams
 * @param pathParams
 * @param authType
 * @param contentType
 * @return - It return the GET response.
 */
	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;

	}
	
//Post
	/**
	 * This method is used to call POST API
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return - It Return the POST Response.
	 */
	public <T>Response post(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
		
	}
	//T body doesn't accept file object hence created a new file method overloading
	public Response post(String baseUrl, String endPoint, File file,
			Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
		
	}
	
	public Response post(String baseUrl, String endPoint, String clinetId, String clientSecret, String grantType, ContentType contentType) {
		Response response =RestAssured.given().contentType(contentType).log().all()
		.formParam("grant_type", grantType)
		.formParam("client_id", clinetId)
		.formParam("client_secret", clientSecret)
		.when()
		.post(baseUrl+endPoint);
		response.prettyPrint();
		return response;
		
	}
	 
	
	public <T>Response put(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
		
	}
	//Patch API
	public <T>Response patch(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.body(body).patch(endPoint).then().spec(responseSpec201).extract().response();
		response.prettyPrint();
		return response;
		
	}
	//Delete API
	public Response delete(String baseUrl, String endPoint,
			Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response= request.delete(endPoint).then().spec(responseSpec204).extract().response();
		response.prettyPrint();
		return response;
		
	}

}
