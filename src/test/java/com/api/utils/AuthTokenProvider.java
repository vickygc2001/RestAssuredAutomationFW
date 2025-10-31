package com.api.utils;

import static io.restassured.RestAssured.*;

import static com.api.constant.Role.*;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	//AuthTokenProvider is a Utility Class and we should not allow anyone to create objects of this class
	//Therefore we are creating a private constructor
	
	private AuthTokenProvider() {
		
	}

	public static String getToken(Role role) throws IOException {
		
		UserCredentials userCredentials = null;
		
//NOTE -		
// If you pass role as a string, below code works but for enum we use second method

//		public static String getToken(String role) throws IOException {
//			
//			UserCredentials userCredentials = null;

//		if(role.equalsIgnoreCase("FD")) {			
//			userCredentials = new UserCredentials("iamfd", "password");
//		} else if(role.equalsIgnoreCase("SUP")) {			
//			userCredentials = new UserCredentials("iamsup", "password");
//		} else if(role.equalsIgnoreCase("ENG")) {			
//			userCredentials = new UserCredentials("iameng", "password");
//		} else if(role.equalsIgnoreCase("QC")) {			
//			userCredentials = new UserCredentials("iamqc", "password");
//		}

		if(role == FD) {			
		userCredentials = new UserCredentials("iamfd", "password");
	} else if(role == SUP) {			
		userCredentials = new UserCredentials("iamsup", "password");
	} else if(role == ENG) {			
		userCredentials = new UserCredentials("iameng", "password");
	} else if(role == QC) {			
		userCredentials = new UserCredentials("iamqc", "password");
	}

	String token = given()
		.baseUri(ConfigManager2.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.body(userCredentials)
	.when()
		.post("login")
	.then()
		.log().ifValidationFails()
		.statusCode(200)
		.body("message", Matchers.equalTo("Success"))
		.extract()
		.body()
		.jsonPath().getString("data.token");
	
		//System.out.println("Toke is : " + token);
		return token;
	}
}
