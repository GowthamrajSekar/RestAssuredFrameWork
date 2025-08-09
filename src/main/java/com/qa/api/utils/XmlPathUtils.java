package com.qa.api.utils;

import java.util.List;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XmlPathUtils {
	
	public static XmlPath getXmlPath(Response response) {
		String responseBody =response.getBody().asString();
		return new XmlPath(responseBody);
	}
	
	public static <T>T  read(Response response, String xmlPathExpression) {
		XmlPath xmlPath =getXmlPath(response);
		return xmlPath.get(xmlPathExpression);
		
	}
	
	public static <T> List<T>  readList(Response response, String xmlPathExpression) {
		XmlPath xmlPath =getXmlPath(response);
		return xmlPath.getList(xmlPathExpression);
	}
	
	
}
