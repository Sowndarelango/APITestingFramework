package com.w2a.APITestingFramework.Utilities;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class StripeAPIDataUtils {
	@DataProvider(name = "StripeAPIDataProvider")
	public Object[][] getTestData(Method method) throws IOException {

		String testName = method.getName();

		return ExcelDataManagerUsingHashtable.getExcelData(".\\src\\test\\resources\\excel\\TestData.xlsx",
				"StripeAPITestData", testName);
	}

}
