package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class MasterAPITest {
	
	@Test
	public void verifyMasterAPITest() throws IOException {
		
		given()
			.baseUri("http://64.227.160.186:9000/v1")
			.and()
			.header("Authorization", AuthTokenProvider.getToken(Role.FD))
			.and()
			.contentType("")  // Content Type should be empty else we'll get 415  error, if no Content type for POST request 
			.log().all()
		.when()
			.post("master")  //default content-type is application/url-formencoded
		.then()
			.log().all()
			.time(Matchers.lessThan(1000L))
			.statusCode(200)
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.notNullValue())
			.body("data", Matchers.hasKey("mst_oem"))
			.body("data", Matchers.hasKey("mst_model"))
			.body("$", Matchers.hasKey("data"))
			.body("$", Matchers.hasKey("message"))
			.body("data.mst_oem.size()", Matchers.greaterThan(0))
			.body("data.mst_model.size()", Matchers.equalTo(3))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
			.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/masterAPIResponseSchema.json"));
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		
		given()
			.baseUri("http://64.227.160.186:9000/v1")
			.and()
			.header("Authorization", " ")
			.and()
			.contentType("")  
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(401);
			
	}

}
