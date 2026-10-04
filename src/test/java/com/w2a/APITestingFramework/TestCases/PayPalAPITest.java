package com.w2a.APITestingFramework.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.File;

import com.w2a.APITestingFramework.ExtentReports.ExtentLogger;
import com.w2a.APITestingFramework.SetUp.BaseTest;

import io.restassured.response.Response;

public class PayPalAPITest extends BaseTest {

	static String access_token;
	static String Order_id;
	static String PayPal_BaseURI = "https://api-m.sandbox.paypal.com";

	@Test(priority = 1)
	public void getAuthKey() {

		Response response = given().auth().preemptive()
				.basic(config.getProperty("PayPalClient_id"), config.getProperty("PayPalClient_pass"))
				.contentType("application/x-www-form-urlencoded").formParam("grant_type", "client_credentials").when()
				.post(PayPal_BaseURI + "/v1/oauth2/token");

	//	response.prettyPrint();

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

		access_token = response.jsonPath().getString("access_token");

		System.out.println("access_token: " + access_token);
	}

	@Test(priority = 2, dependsOnMethods = "getAuthKey")
	public void createOrder() {

		File CreateOrder_Body = new File("src/test/resources/PayPalAPI_TestJSON/CreateOrder.json");
		Response response = given().auth().oauth2(access_token).contentType("application/json").body(CreateOrder_Body)
				.when().post(PayPal_BaseURI + "/v2/checkout/orders");

		response.prettyPrint();

		ExtentLogger.info("PayPal Create Order Response:");
		ExtentLogger.info("<pre>" + response.asPrettyString() + "</pre>");

		System.out.println("Status Code: " + response.getStatusCode());
		
		Order_id = response.jsonPath().getString("id");

		System.out.println("Order_id: " + Order_id);

		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test(priority = 3, dependsOnMethods = { "getAuthKey", "createOrder" })
	public void getOrder() {

		Response response = given().auth().oauth2(access_token).contentType("application/json").when()
				.get(PayPal_BaseURI + "/v2/checkout/orders/" + Order_id);

		response.prettyPrint();

		ExtentLogger.info("PayPal GET Order Response:");
		ExtentLogger.info("<pre>" + response.asPrettyString() + "</pre>");

		System.out.println("Status Code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);

	}

}
