package com.w2a.APITestingFramework.APIs;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.w2a.APITestingFramework.ExtentReports.ExtentLogger;
import com.w2a.APITestingFramework.SetUp.BaseTest;

import io.restassured.response.Response;

public class CreateCustomerAPI extends BaseTest {

	public static Response sendPostRequestToCreateCustomerAPIWithValidAuthKey(Hashtable<String, String> data) {
		Response response = given().auth().basic(config.getProperty("StripeAPIValidSecretKey"), "")
				.formParam("name", data.get("name")).formParam("email", data.get("email"))
				.formParam("description", data.get("description"))
				.post(config.getProperty("StripeAPICustomerEndpoint"));

		ExtentLogger.response(response.getStatusCode(), response.asPrettyString());
		
		return response;

	}

	public static Response sendPostRequestToCreateCustomerAPIWithInValidAuthKey(Hashtable<String, String> data) {
		Response response = given().auth().basic(config.getProperty("StripeAPIinValidSecretKey"), "")
				.formParam("name", data.get("name")).formParam("email", data.get("email"))
				.formParam("description", data.get("description"))
				.post(config.getProperty("StripeAPICustomerEndpoint"));
		
		ExtentLogger.response(response.getStatusCode(), response.asPrettyString());

		return response;

	}
}
