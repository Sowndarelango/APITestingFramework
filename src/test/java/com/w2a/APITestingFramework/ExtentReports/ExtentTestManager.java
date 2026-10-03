package com.w2a.APITestingFramework.ExtentReports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private static ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    public static void startTest(String testName) {

        ExtentTest test =
                ExtentManager.getExtentReports()
                             .createTest(testName);

        extentTest.set(test);
    }

    public static ExtentTest getTest() {

        return extentTest.get();
    }

    public static void removeTest() {

        extentTest.remove();
    }
}
