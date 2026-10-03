package com.w2a.APITestingFramework.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.SetUp.BaseTest;
import com.w2a.APITestingFramework.Utilities.DataUtils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Sample extends BaseTest {

	
	// Testing Jenkins Poll SCM
	@Test(dataProvider = "StripeAPICreateCustomer", dataProviderClass = DataUtils.class)
	public void validateCreateCustomerAPIWithValidSecretKey(String name, String email, String description) {
		Response response = given().auth().basic(config.getProperty("StripeAPIValidSecretKey"), "")
				.formParam("name", name).formParam("email", email).formParam("description", description)
				.post(config.getProperty("StripeAPICustomerEndpoint"));

		response.prettyPrint();

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(dataProvider = "StripeAPICreateCustomerDataforInvalidKey", dataProviderClass = DataUtils.class)
	public void validateCreateCustomerAPIWithInvalidSecretKey(String name, String email, String description) {
		Response response = given().auth().basic(config.getProperty("StripeAPIinValidSecretKey"), "")
				.formParam("name", name).formParam("email", email).formParam("description", description)
				.post(config.getProperty("StripeAPICustomerEndpoint"));

		response.prettyPrint();

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

	}

}
