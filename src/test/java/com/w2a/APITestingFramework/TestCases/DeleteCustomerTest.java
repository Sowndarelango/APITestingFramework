package com.w2a.APITestingFramework.TestCases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.DeleteCustomerAPI;
import com.w2a.APITestingFramework.ExtentReports.ExtentLogger;
import com.w2a.APITestingFramework.SetUp.BaseTest;
import com.w2a.APITestingFramework.Utilities.StripeAPIDataUtils;

import io.restassured.response.Response;

public class DeleteCustomerTest extends BaseTest {

	@Test(dataProvider = "StripeAPIDataProvider", dataProviderClass = StripeAPIDataUtils.class)
	public void DeleteCustomer(Hashtable<String, String> data) {
		Response response = DeleteCustomerAPI.sendDeleteRequestToDeleteCustomerAPIWithValidID(data);

		response.prettyPrint();
		
		ExtentLogger.info("Deleting customer for ID: " + data.get("id"));

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
