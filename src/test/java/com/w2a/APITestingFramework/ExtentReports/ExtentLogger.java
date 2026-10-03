package com.w2a.APITestingFramework.ExtentReports;

import com.aventstack.extentreports.Status;

public class ExtentLogger {

    public static void info(String message) {
        ExtentTestManager.getTest().log(Status.INFO, message);
    }

    public static void pass(String message) {
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        ExtentTestManager.getTest().log(Status.FAIL, message);
    }

    public static void skip(String message) {
        ExtentTestManager.getTest().log(Status.SKIP, message);
    }

    public static void request(String method, String url, String body) {

        ExtentTestManager.getTest().info(
                "<b>REQUEST</b><br>" +
                "Method: " + method + "<br>" +
                "URL: " + url + "<br>" +
                "Body:<br><pre>" + body + "</pre>"
        );
    }

    public static void response(int statusCode, String body) {

        ExtentTestManager.getTest().info(
                "<b>RESPONSE</b><br>" +
                "Status Code: " + statusCode + "<br>" +
                "Body:<br><pre>" + body + "</pre>"
        );
    }
}