package com.w2a.APITestingFramework.TestCases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.CreateCustomerAPI;
import com.w2a.APITestingFramework.ExtentReports.ExtentLogger;
import com.w2a.APITestingFramework.SetUp.BaseTest;
import com.w2a.APITestingFramework.Utilities.StripeAPIDataUtils;

import io.restassured.response.Response;

public class CreateCustomerTest extends BaseTest {

	@Test(dataProvider = "StripeAPIDataProvider", dataProviderClass = StripeAPIDataUtils.class)
	public void validateCreateCustomerAPIWithValidSecretKey(Hashtable<String, String> data) {
		Response response = CreateCustomerAPI.sendPostRequestToCreateCustomerAPIWithValidAuthKey(data);

		response.prettyPrint();

		ExtentLogger.info("Creating customer: " + data.get("name"));
		ExtentLogger.info("Customer email: " + data.get("email"));

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(dataProvider = "StripeAPIDataProvider", dataProviderClass = StripeAPIDataUtils.class)
	public void validateCreateCustomerAPIWithInvalidSecretKey(Hashtable<String, String> data) {
		Response response = CreateCustomerAPI.sendPostRequestToCreateCustomerAPIWithInValidAuthKey(data);

		response.prettyPrint();

		ExtentLogger.info("Creating customer: " + data.get("name"));
		ExtentLogger.info("Customer email: " + data.get("email"));

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

	}
}
