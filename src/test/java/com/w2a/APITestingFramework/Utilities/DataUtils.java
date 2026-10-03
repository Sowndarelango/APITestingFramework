package com.w2a.APITestingFramework.Utilities;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class DataUtils {

	@DataProvider(name = "StripeAPICreateCustomer")
	public Object[][] getTestData() throws IOException {

		return ExcelUtils.getExcelData(".\\src\\test\\resources\\excel\\TestData.xlsx", "StripeAPICreateCustomer");
	}

	@DataProvider(name = "StripeAPICreateCustomerDataforInvalidKey")
	public Object[][] getTestData2(Method method) throws IOException {

		String testName = method.getName();

		return ExcelDataManager.getExcelData(".\\src\\test\\resources\\excel\\TestData.xlsx", "StripeAPITestData",
				testName);
	}

}
