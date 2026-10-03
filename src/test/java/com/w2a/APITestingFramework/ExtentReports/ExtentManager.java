package com.w2a.APITestingFramework.ExtentReports;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

	private static ExtentReports extent;

	public static synchronized ExtentReports getExtentReports() {

		if (extent == null) {

			String timestamp = LocalDateTime.now()
			        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
			
			String reportPath = System.getProperty("user.dir") + File.separator + "test-output/ExtentReports/ExtentReport_" + timestamp + ".html";

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

			// Report title
			sparkReporter.config().setDocumentTitle("API Automation Test Report");

			// Report name
			sparkReporter.config().setReportName("Rest Assured API Automation Results");

			// Dark theme
			sparkReporter.config().setTheme(Theme.DARK);

			// Date format
			sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

			extent = new ExtentReports();

			extent.attachReporter(sparkReporter);

			// System information
			extent.setSystemInfo("Application", "Stripe API");
			extent.setSystemInfo("Automation", "Rest Assured");
			extent.setSystemInfo("Framework", "TestNG");
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}

		return extent;
	}

	public static synchronized void flush() {

		if (extent != null) {
			extent.flush();
		}
	}
}