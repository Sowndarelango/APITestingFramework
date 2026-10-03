package com.w2a.APITestingFramework.ExtentReports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		ExtentTestManager.startTest(testName);

		ExtentTestManager.getTest().info("Test started");

		ExtentTestManager.getTest().info("Class: " + result.getTestClass().getName());

		ExtentTestManager.getTest().info("Method: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTestManager.getTest().pass("TEST PASSED");

		addExecutionDetails(result);

		ExtentTestManager.removeTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTestManager.getTest().fail("TEST FAILED");

		if (result.getThrowable() != null) {

			ExtentTestManager.getTest().fail(result.getThrowable());
		}

		addExecutionDetails(result);

		ExtentTestManager.removeTest();
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentTestManager.getTest().skip("TEST SKIPPED");

		if (result.getThrowable() != null) {

			ExtentTestManager.getTest().skip(result.getThrowable());
		}

		addExecutionDetails(result);

		ExtentTestManager.removeTest();
	}

	private void addExecutionDetails(ITestResult result) {

		long startTime = result.getStartMillis();
		long endTime = result.getEndMillis();

		long duration = endTime - startTime;

		ExtentTestManager.getTest().info("Start Time: " + startTime);

		ExtentTestManager.getTest().info("Execution Time: " + duration + " ms");
	}

	@Override
	public void onFinish(ITestContext context) {

		ExtentManager.flush();
	}
}