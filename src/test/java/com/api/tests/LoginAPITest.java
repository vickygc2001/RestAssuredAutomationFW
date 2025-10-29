package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import com.api.utils.ConfigManager2;
//import static com.api.utils.ConfigManager2.*; and remove the class name when you call the getProperty method


import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

//NOTE : RESTASSURED is a UTILITY CLASS, and we use static keyword to access the methods directly
//import io.restassured.RestAssured;

public class LoginAPITest {
	
	
	@Test
	public void loginAPITest() throws IOException {
		
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		
			given()
			//If you import ConfigManager as static you can access method directly without giving the class name
				//.baseUri(getProperty("BASE_URI"))
				.baseUri(ConfigManager2.getProperty("BASE_URI"))
				.and()
				.contentType(ContentType.JSON)   //Contenttype is a helper method
				.and()
				.accept(ContentType.JSON)
				.and()
				.body(userCredentials)
				.log().uri()
				.log().method()
				.log().headers()
				.log().body()
			.when()
				.post("/login")
			.then()
				.log().all()
				.statusCode(200)
				.time(Matchers.lessThan(1500L))
				.and()
				.body("message", Matchers.equalTo("Success"))
				.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/loginAPIResponseSchema.json"))
				;
				
				
		
	}

}
