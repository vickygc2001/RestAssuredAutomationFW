package com.api.tests;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

//NOTE : RESTASSURED is a UTILITY CLASS, and we use static keyword to access the methods directly
//import io.restassured.RestAssured;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() {
		
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		
			given()
				.baseUri("http://64.227.160.186:9000/v1")
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
