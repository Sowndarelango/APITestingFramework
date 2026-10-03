package com.w2a.APITestingFramework.APIs;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.w2a.APITestingFramework.ExtentReports.ExtentLogger;
import com.w2a.APITestingFramework.SetUp.BaseTest;

import io.restassured.response.Response;

public class DeleteCustomerAPI extends BaseTest {

	public static Response sendDeleteRequestToDeleteCustomerAPIWithValidID(Hashtable<String, String> data) {
		Response response = given().auth().basic(config.getProperty("StripeAPIValidSecretKey"), "")
				.delete(config.getProperty("StripeAPICustomerEndpoint")+"/" + data.get("id"));
		
		ExtentLogger.response(response.getStatusCode(), response.asPrettyString());

		return response;

	}
}
