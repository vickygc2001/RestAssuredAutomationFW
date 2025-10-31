package com.api.tests;

import static com.api.constant.Role.*;
import com.api.utils.AuthTokenProvider;
import  com.api.utils.ConfigManager;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
		//If you are passing a string as an argument in getToken() function, but we are using Enum
		//Header authHeader = new Header("Authorization", AuthTokenProvider.getToken("eng"));
		
		Header authHeader = new Header("Authorization", AuthTokenProvider.getToken(FD));
		
			String firstName = given()
				.baseUri(ConfigManager.getProperty("BASE_URI"))
				.and()
				.header(authHeader)
				.and()
				.accept(ContentType.JSON)
				.log().uri()
				.log().headers()
			.when()
				.get("userdetails")
			.then()
				.log().all()
				.statusCode(200)
				.and()
				.time(Matchers.lessThan(1500L))
				.and()
				.body("message", Matchers.equalTo("Success"))
				.and()
			//	.body("data.id", Matchers.equalTo(4))
			//	.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/userDetailsAPIResponseSchema.json"))
				//.extract().path("data.first_name");
				.extract().jsonPath().getString("data.first_name")
				;
				
				System.out.println(firstName);
		
	}

}
