package com.api.tests;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.module.jsv.JsonSchemaValidator;

@Test
public class CountAPITest {
	
	public void verifyCountAPITest() throws IOException {
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI")) 	//Can do a static import here as well
			.and()
			.header("Authorization", AuthTokenProvider.getToken(FD))
			.log().uri()
			.log().headers()
			.log().method()
		.when()
			.get("dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.and()
			.time(Matchers.lessThan(1000L))
			.and()
			.body("message", Matchers.equalTo("Success"))
			.body("data.size()", Matchers.equalTo(3))
			.body("data", Matchers.notNullValue())
			.body("data.count", Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.body("data.label", Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.body("data.key", Matchers.containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("responseSchema/countAPIResponseSchema-FD.json"))
			;
	}
	
	@Test
	public void countAPITest_MissingAuthToken() throws IOException {
			
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI")) 	//Can do a static import here as well
			.log().uri()
			.log().headers()
			.log().method()
		.when()
			.get("dashboard/count")
		.then()
			.log().all()
			.statusCode(401);
		}
	
}
